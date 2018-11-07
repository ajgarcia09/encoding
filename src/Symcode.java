public class Symcode {
    public static void main(String[]args){
        //readFile();
        String str = "gimble";
        String binaryString = buildBinaryString(str);
        System.out.println(binaryString);

        String subset = "";
        System.out.println("binaryString.length() = " + binaryString.length());
        StringBuilder symBuilder = new StringBuilder();
        for(int i = 0; i <= binaryString.length()-2; i = i+3){
            System.out.println("i = " +i);
            System.out.println("i+2 = " + (i+3));
            subset = binaryString.substring(i,i+3);
            System.out.println("subset is: " + subset);
            int symIndex = computeSymIndex(subset);
            char symcoded = computeSymcode(symIndex);

        }
    }

    public static char computeSymcode(int symIndex) {
        char symcoded = ' ';
        switch(symIndex){
            case 0:
                symcoded = 's';
                break;
            case 1:
                symcoded = 'y';
                break;
            case 2:
                symcoded = 'm';
                break;
            case 3:
                symcoded = 'a';
                break;
            case 4:
                symcoded = 'n';
                break;
            case 5:
                symcoded = 't';
                break;
            case 6:
                symcoded = 'e';
                break;
            case 7:
                symcoded = 'c';
                break;
        }
        System.out.println("symcoded: " + symcoded);
        return symcoded;
    }

    public static int computeSymIndex(String subset) {
        int sum =0;
        if(subset.charAt(2) == '1'){
            sum += 1;
        }
        if(subset.charAt(1) == '1'){
            sum += 2;
        }
        if(subset.charAt(0) == '1'){
            sum +=4;
        }
        System.out.println("sum is: " + sum);
        return sum;
    }

    public static String buildBinaryString(String str) {
        //pad string
       // String paddedStr = padString(str);
        char current = ' ';
        StringBuilder builder = new StringBuilder();


        for(int i=0; i<str.length();i++){
            current = str.charAt(i);
            int ascii = (int) current;
            String hexString = Integer.toHexString(ascii);
            int hexInt = Integer.parseInt(hexString,16);
            String binary = "0" + Integer.toBinaryString(hexInt);
            System.out.println("char: " + current + " hex: " + hexString + " binary: " + binary + " ");
            builder.append(binary);
        }
        System.out.println("Entire string:");
        return builder.toString();
    }
}