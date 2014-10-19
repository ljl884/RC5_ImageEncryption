package test;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.RC5Parameters;

public class RC516Engine 
	implements BlockCipher{
	
	private static final int wordSize = 16;
	private static final int bytesPerWord = wordSize / 8;
	  /*
     * the number of rounds to perform
     */
    private int _noRounds;

    /*
     * the expanded key array of size 2*(rounds + 1)
     */
    private int _S[];

    /*
     * our "magic constants" for 16
     *
     * Pw = Odd((e-2) * 2^wordsize)
     * Qw = Odd((o-2) * 2^wordsize)
     *
     * where e is the base of natural logarithms (2.718281828...)
     * and o is the golden ratio (1.61803398...)
     */
    //TODO: STILL P32
    private static final int P16 = 0xb7e1;
    private static final int Q16 = 0x9e37;

    private boolean forEncryption;

    /**
     * Create an instance of the RC5 encryption algorithm
     * and set some defaults
     */
    public RC516Engine()
    {
        _noRounds     = 12;         // the default
        _S            = null;
    }

    public String getAlgorithmName()
    {
        return "RC5-16";
    }

    public int getBlockSize()
    {
        return 2 * bytesPerWord;
    }

    /**
     * initialise a RC5-16 cipher.
     *
     * @param forEncryption whether or not we are for encryption.
     * @param params the parameters required to set up the cipher.
     * @exception IllegalArgumentException if the params argument is
     * inappropriate.
     */
    public void init(
        boolean             forEncryption,
        CipherParameters    params)
    {
        if (params instanceof RC5Parameters)
        {
            RC5Parameters       p = (RC5Parameters)params;

            _noRounds     = p.getRounds();

            setKey(p.getKey());
        }
        else if (params instanceof KeyParameter)
        {
            KeyParameter       p = (KeyParameter)params;

            setKey(p.getKey());
        }
        else
        {
            throw new IllegalArgumentException("invalid parameter passed to RC532 init - " + params.getClass().getName());
        }

        this.forEncryption = forEncryption;
    }

    public int processBlock(
        byte[]  in,
        int     inOff,
        byte[]  out,
        int     outOff)
    {
        return (forEncryption) ? encryptBlock(in, inOff, out, outOff) 
                               : decryptBlock(in, inOff, out, outOff);
    }

    public void reset()
    {
    }

    /**
     * Re-key the cipher.
     * <p>
     * @param  key  the key to be used
     */
    public void setKey(
        byte[]      key)
    {
        //
        // KEY EXPANSION:
        //
        // There are 3 phases to the key expansion.
        //
        // Phase 1:
        //   Copy the secret key K[0...b-1] into an array L[0..c-1] of
        //   c = ceil(b/u), where u = 16/8 in little-endian order.
        //   In other words, we fill up L using u consecutive key bytes
        //   of K. Any unfilled byte positions in L are zeroed. In the
        //   case that b = c = 0, set c = 1 and L[0] = 0.
        //
        int[]   L = new int[(key.length + (bytesPerWord - 1)) / bytesPerWord];
        int mask = 0;
        //System.out.println(Arrays.toString(L));
        //System.out.println("i"+ "    " +"0xff"+ "    " + "key[i] & 0xff" + "    " + "L[i / 4]"+"   "+"8 * (i % 4)");
        for (int i = 0; i != key.length; i++)
        {
            mask = (key[i] & 0xff);
        	L[i / bytesPerWord] = L[i / bytesPerWord] + (key[i] & 0xff) << (8 * (i % bytesPerWord));
        	
        	//System.out.println( i + "    " + mask + "    " +  (key[i] & 0xff)  + "    " +  L[i / 4] +"   "+ 8 * (i % 4) );
        }
        //System.out.println("This is L:   " +Arrays.toString(L));
        //
        // Phase 2:
        //   Initialize S to a particular fixed pseudo-random bit pattern
        //   using an arithmetic progression modulo 2^wordsize determined
        //   by the magic numbers, Pw & Qw.
        //
        _S            = new int[2*(_noRounds + 1)];

        _S[0] = P16;
        //System.out.println(Arrays.toString(_S));
        for (int i=1; i < _S.length; i++)
        {
            _S[i] = (_S[i-1] + Q16);
           // System.out.print(i + "---->");
           // System.out.println(Arrays.toString(_S));
        }

        //
        // Phase 3:
        //   Mix in the user's secret key in 3 passes over the arrays S & L.
        //   The max of the arrays sizes is used as the loop control
        //
        // System.out.println("This is S------------------------");
        int iter;

        if (L.length > _S.length)
        {
            iter = 3 * L.length;
        }
        else
        {
            iter = 3 * _S.length;
        }

        int A = 0, B = 0;
        int i = 0, j = 0;

        for (int k = 0; k < iter; k++)
        {
            A = _S[i] = rotateLeft(_S[i] + A + B, 3);
            B =  L[j] = rotateLeft(L[j] + A + B, A+B);
            i = (i+1) % _S.length;
            j = (j+1) %  L.length;
//            System.out.print(k + "---->");
//            System.out.print(A + "--------");
//            System.out.println(B);
        }
    }

    /**
     * Encrypt the given block starting at the given offset and place
     * the result in the provided buffer starting at the given offset.
     * <p>
     * @param  in     in byte buffer containing data to encrypt
     * @param  inOff  offset into src buffer
     * @param  out     out buffer where encrypted data is written
     * @param  outOff  offset into out buffer
     */
    private int encryptBlock(
        byte[]  in,
        int     inOff,
        byte[]  out,
        int     outOff)
    {
        int A = bytesToWord(in, inOff) + _S[0];
        int B = bytesToWord(in, inOff + bytesPerWord) + _S[1];
//
//       
//        System.out.print(A + "--------");
//        System.out.println(B);
//        
       
        for (int i = 1; i <= _noRounds; i++)
        {
            A = rotateLeft(A ^ B, B) + _S[2*i];
            B = rotateLeft(B ^ A, A) + _S[2*i+1];
        }
        
        wordToBytes(A, out, outOff);
        wordToBytes(B, out, outOff + bytesPerWord);
        //System.out.println("This is out:" + Arrays.toString(out) + Hex.toHexString( out ));
        
        return 2 * bytesPerWord;
    }

    private int decryptBlock(
        byte[]  in,
        int     inOff,
        byte[]  out,
        int     outOff)
    {
        int A = bytesToWord(in, inOff);
        int B = bytesToWord(in, inOff + bytesPerWord);

        for (int i = _noRounds; i >= 1; i--)
        {
            B = rotateRight(B - _S[2*i+1], A) ^ A;
            A = rotateRight(A - _S[2*i],   B) ^ B;
        }
        
        wordToBytes(A - _S[0], out, outOff);
        wordToBytes(B - _S[1], out, outOff + bytesPerWord);
        
        return 2 * bytesPerWord;
    }

    
    //////////////////////////////////////////////////////////////
    //
    // PRIVATE Helper Methods
    //
    //////////////////////////////////////////////////////////////

    /**
     * Perform a left "spin" of the word. The rotation of the given
     * word <em>x</em> is rotated left by <em>y</em> bits.
     * Only the <em>lg(32)</em> low-order bits of <em>y</em>
     * are used to determine the rotation amount. Here it is 
     * assumed that the wordsize used is a power of 2.
     * <p>
     * @param  x  word to rotate
     * @param  y    number of bits to rotate % 32
     */
    public int rotateLeft(int x, int y)
    {
        return ((x << (y & (wordSize-1))) | (x >>> (wordSize - (wordSize & (32-1)))));
    }

    /**
     * Perform a right "spin" of the word. The rotation of the given
     * word <em>x</em> is rotated left by <em>y</em> bits.
     * Only the <em>lg(32)</em> low-order bits of <em>y</em>
     * are used to determine the rotation amount. Here it is 
     * assumed that the wordsize used is a power of 2.
     * <p>
     * @param  x  word to rotate
     * @param  y    number of bits to rotate % 32
     */
    private int rotateRight(int x, int y)
    {
        return ((x >>> (y & (wordSize-1))) | (x << (wordSize - (y & (wordSize-1)))));
    }

    public int bytesToWord(
        byte[]  src,
        int     srcOff)
    {
    	int word = 0;
    	 for (int i = bytesPerWord - 1; i >= 0; i--)
         {
             word = (word << 8) + (src[i + srcOff] & 0xff);
         }
    	return word;
    }

    public void wordToBytes(
        int    word,
        byte[]  dst,
        int     dstOff)
    {
        for (int i = 0; i < bytesPerWord; i++)
        {
            dst[i + dstOff] = (byte)word;
            word >>>= 8;
        }
    }

}
