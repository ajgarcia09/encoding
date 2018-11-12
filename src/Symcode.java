import java.io.*;

public class Symcode {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/ajgarcia09/Documents/Symantec/src/myFile.txt")))
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/ajgarcia09/Documents/Symantec/src/output.txt"));
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                String str = sCurrentLine;
                StringBuilder symBuilder = new StringBuilder();
                //how many bits str will take up
                int bitSize = str.length() * 8;
                int remainder = bitSize % 24; //how many bits in the next slot of size 24 bits
                if (remainder != 0) {
                    int padBits = 24 - remainder; //how many bits need padding
                    if (bitSize % 3 != 0) {
                        String binaryString = buildBinaryString(str, padBits);
                        binaryString = binaryString + '0';//add an extra bit at the end
                        //encode
                        symBuilder = encode(symBuilder, binaryString,bitSize);
                        //write the encoded string to the output file
                        writer.write(symBuilder.toString());
                        writer.newLine();
                        System.out.println("Final symBuilder with padding: " + symBuilder.toString());
                    } else {
                        String binaryString = buildBinaryString(str,0);
                        //encode with padding
                        symBuilder = encode(symBuilder,binaryString,bitSize);
                        //write the encoded string to the output file
                        writer.write(symBuilder.toString());
                        writer.newLine();
                        System.out.println("Final symBuilder with padding: " + symBuilder.toString());
                    }
                } else { //encode, no need to pad
                    String binaryString = buildBinaryString(str,0);
                    binaryString = binaryString;
                    //encode without padding
                    symBuilder = encode(symBuilder,binaryString);
                    //write the encoded string to the output file
                    writer.write(symBuilder.toString());
                    writer.newLine();
                    System.out.println("Final symBuilder with padding: " + symBuilder.toString());
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //encodes without padding
    public static StringBuilder encode(StringBuilder symBuilder, String binaryString){
        String subset = "";
        for(int i = 0;i <= binaryString.length() - 3;i =i+3){
            subset = binaryString.substring(i, i + 3);
            int symIndex = computeSymIndex(subset);
            char symcoded = computeSymcode(symIndex);
            symBuilder.append(symcoded);
        }

        return symBuilder;
    }

    //encodes with padding
    public static StringBuilder encode(StringBuilder symBuilder, String binaryString, int encodingBits) {
        String subset = "";
        int remainder = encodingBits % 3;
        int neededBits = 3 - remainder;
        int upperBound = encodingBits + neededBits;
        for(int i = 0;i <= upperBound - 3;i =i+3){
            subset = binaryString.substring(i, i + 3);
            int symIndex = computeSymIndex(subset);
            char symcoded = computeSymcode(symIndex);
            symBuilder.append(symcoded);
        }

        StringBuilder padBuilder = new StringBuilder();
        String padSubset = "";
        for(int i = upperBound; i <= (binaryString.length()-1)-3; i = i+3){
            padSubset = binaryString.substring(i, i+3);
            padBuilder.append('$');
        }

        //add the padding to the end of the encoded string
        symBuilder.append(padBuilder.toString());

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
            builder.append(binary);
        }
        System.out.println(builder.toString());

        //add the padding 0s portion
        StringBuilder paddingBinStr = new StringBuilder();
        for (int i = 0; i < padBits; i++) {
            paddingBinStr.append('0');
        }

        String paddingString = paddingBinStr.toString();
        builder.append(paddingString);


        return builder.toString();
    }
}