public class Symcode {
    public static void main(String[] args) {
        String str = "";
        StringBuilder symBuilder = new StringBuilder();
        //how many bits str will take up
        int bitSize = str.length() * 8;
        System.out.println("bitSize = " + bitSize);
        int remainder = bitSize % 24; //how many bits in the next slot of size 48 bits
        System.out.println("remainder = " + remainder);
        if (remainder != 0) {
            System.out.println("Remainder != 0");
            int padBits = 24 - remainder; //how many bits need padding
            System.out.println("padBits = " + padBits);
            if (bitSize % 3 != 0) { //wabe, mimsy, MACBETH
                System.out.println("bitSize%3 !=0");
                String binaryString = buildBinaryString(str, padBits);
                binaryString = binaryString + '0';//add an extra bit at the end
                System.out.println("binaryString with extra bit at the end: ");
                System.out.println(binaryString);
                //encode
                symBuilder = encode(symBuilder, binaryString);
                //add some padding
                //subtract the bit we added to binaryString
                System.out.println("Subtracting 1 from padBits:");
                padBits = padBits- 1;
                System.out.println("padBits = " + padBits);
               // symBuilder = pad(padBits,symBuilder);
                System.out.println("Final symBuilder with padding: " + symBuilder.toString());
            } else {
                System.out.println("bitSize %3 == 0");
                String binaryString = buildBinaryString(str,0);
                System.out.println(binaryString);
                //encode
                symBuilder = encode(symBuilder,binaryString);
                //add some padding
               // symBuilder = pad(padBits, symBuilder);
                System.out.println("Final symBuilder with padding: " + symBuilder.toString());
            }
            //gimble
        } else { //encode, no need to pad
            System.out.println("remainder == 0");
            System.out.println("entered outer else");
            String binaryString = buildBinaryString(str,0);
            binaryString = binaryString;
            System.out.println(binaryString);
            //encode
            symBuilder = encode(symBuilder,binaryString);
        }
    }

   /* public static StringBuilder pad(int bitSize,int padBits, StringBuilder symBuilder,) {
        for (int i = bitSize+1; i <= padBits - 3; i = i + 3) {
            String padSubset = paddingString.substring(i, i + 3);
            System.out.println("padSubset = " + padSubset);
            symBuilder.append('$');
            System.out.println("symbuilder with padding: " + symBuilder.toString());
        }
        return symBuilder;
    }*/

    public static StringBuilder encode(StringBuilder symBuilder, String binaryString) {
        String subset = "";
        int remainder = binaryString.length() % 3;
        int upperBound = binaryString.length() + remainder;
        System.out.println("binaryString.length() = "+binaryString.length());
        for(int i = 0;i < upperBound - 3;i =i+3){
            System.out.println("binaryString.length()-3: " + (binaryString.length() - 3));
            System.out.println("i = " + i);

            subset = binaryString.substring(i, i + 3);
            System.out.println("subset = " + subset);
            int symIndex = computeSymIndex(subset);
            char symcoded = computeSymcode(symIndex);
            symBuilder.append(symcoded);
            System.out.println("symBuilder now: " + symBuilder.toString());
        }
//        if(remainder != 0){
//            if(remainder == 1){
//                symBuilder.append(binaryString.charAt(binaryString.length() -1) + binaryString.charAt(binaryString.length() - 2) + '0');
//
//            }
//            if(remainder == 2){
//                symBuilder.append(binaryString.charAt(binaryString.length() - 1) + '0' + '0');
//            }
//        }
        return symBuilder;
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
        System.out.println("Subset in computeSymIndex: " + subset);
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

    public static String buildBinaryString(String str, int padBits) {

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
        System.out.println(builder.toString());

        //add the padding 0s portion
        StringBuilder paddingBinStr = new StringBuilder();
        for (int i = 0; i < padBits; i++) {
            paddingBinStr.append('0');
            System.out.println("paddingBinStr: " + paddingBinStr);
        }
        System.out.println("Final paddingBinStr: " + paddingBinStr);

        String paddingString = paddingBinStr.toString();
        builder.append(paddingString);


        return builder.toString();
    }
}