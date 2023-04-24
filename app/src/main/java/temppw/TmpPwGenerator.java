package temppw;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

class TmpPwGenerator{

    private Random random;
    //private String algrthmStr = "";
    //private long seedVal = 0L;

    private String[] specialStr = {"!", "@", "#","$", "%","^", "&","*"};

    private List<Integer> numHstLst;

    public TmpPwGenerator(){
        initRandomObj();
    }

    public void initRandomObj(){
        try {

            this.random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            this.random.setSeed(new Date().getTime());
            initNumHstList();

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /****
     * 
     * @param len
     * length of result
     * @param opt
     *  => "UPPER", "LOWER", "SPCL", "NUM"
     * @return tempPw
     */
    public String createTmpPsWd(int len, String ...opt) throws IllegalArgumentException{

        if( opt == null || opt.length == 0){
            opt = new String[]{"UPPER", "LOWER", "SPCL", "NUM"};
        }
        
        String resTmpPw = "";
        int typeNum = 0;

        initNumHstList();
        
        //Random random = SecureRandom.getInstanceStrong(); // has performence issue...
        // instead of this 
        //Random random = new SecureRandom(); //create default algorithm SecureRandom


        for(int i = 0 ; i < len ; i++){
            typeNum = random.nextInt(0, opt.length);
            String typeStr = opt[typeNum].toUpperCase();

            if("NUM".equals(typeStr)){
                resTmpPw += String.valueOf(getRandomNumeric(0, 10));
            }
            else if("UPPER".equals(typeStr)){
                resTmpPw += getUpperLetter();
            }
            else if("LOWER".equals(typeStr)){
                resTmpPw += getLowerLetter();
            }
            else if("SPCL".equals(typeStr)){
                resTmpPw += getRandomSpecial();
            }
            else{
                throw new IllegalArgumentException("Invaild opt args... Check context of opt args");
            }

        }
    
        
        return resTmpPw;
        
    }

    protected void initNumHstList(){
        if(numHstLst == null){
            numHstLst = new ArrayList<>();
        }
        else{
            numHstLst.clear();
        }
    }

    protected boolean isDupNum(int num){
        return numHstLst.contains(num);
    }

    protected int getRandomNumeric(int origin, int bound){
        int ranNum = random.nextInt(origin, bound);
        while(isDupNum(ranNum)){
            ranNum = random.nextInt(origin, bound);
        }
        this.numHstLst.add(ranNum);
        return ranNum;
    }

    protected String getRandomSpecial(){
        return specialStr[getRandomNumeric(0, 7)];
    }

    protected String getLowerLetter(){
        return Character.toString(getRandomNumeric(97, 123));
    }

    protected String getUpperLetter(){
        return Character.toString(getRandomNumeric(65, 91));
    }

    
}