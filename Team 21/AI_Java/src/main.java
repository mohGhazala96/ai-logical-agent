import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class main {
	static String knowledgeBase;
	static String tempString;
	public static void GenGrid (String grid) {
		knowledgeBase="";
		tempString="stonesF(";
		String[] inputSpilt = grid.split(";");
		// [m,n] ;
		// [ix,iy] ;
		// [tx,ty];
		// [s1 ,x,s1 y,s2 x,s2 y,s3 x,s3 y,s4 x,s4 y,s5 x,s5 y,s6 x,s6 y];
		//grid(5,5).
		//thanos(3,4).
		//stonesF([(3,3)|[(2,2)|[(2,1)|[(1,1)|[]]]]]).
		//ironman((1, 2), [], s0).
		String rows = (inputSpilt[0].split(",")[0]); // [m,n] element 0 ->m
		String cols = (inputSpilt[0].split(",")[1]); // [m,n] element 1 -> n
		knowledgeBase+="grid(" +rows+ ","+ cols +").\n";
		String IPosx = (inputSpilt[1].split(",")[0]);// [ix,iy] element 0 ->ix
		String IPosy = (inputSpilt[1].split(",")[1]); // [ix,iy] element 1 ->iy
		knowledgeBase+="ironman((" +IPosx+ ","+ IPosy +"),[],"+"s0).\n";

		String tx = inputSpilt[2].split(",")[0]; // [tx,ty] elemnt 0-> tx
		String ty = inputSpilt[2].split(",")[1]; // [tx,ty] elemnt 1-> ty

		knowledgeBase+="thanos(" +tx+ ","+ ty+").\n";

		// for stones
		for (int i = inputSpilt[3].split(",").length-2; i >= 0; i -= 2) {
			String sx = inputSpilt[3].split(",")[i]; // [sx,sy] elemnt 0-> sx
			String sy = inputSpilt[3].split(",")[i + 1]; // [sx,sy] elemnt 1-> sy
		knowledgeBase+=tempString+"[(" +sx+ ","+ sy+")|";
		tempString = "";
		}
		knowledgeBase+="[]]]]]).";

	}

	public static void main(String args[]) throws IOException {
			GenGrid("4,4;0,0;3,3;1,1,1,2,2,2,2,3");
			//5,5;1,2;3,4;1,1,2,1,2,2,3,3   KB1
			//4,4;0,0;3,3;1,1,1,2,2,2,2,3   KB2
			System.out.println(knowledgeBase);

		   	String value = knowledgeBase;
		    FileOutputStream outputStream = new FileOutputStream("../AI_Prolog/KB2.pl");
		    byte[] strToBytes = value.getBytes();
		    outputStream.write(strToBytes);
		 
		    outputStream.close();
	}
}
