import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class University {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File(args[0]));      
		Person p[] = new Person[1];
		int idx = 0;
		String a,b,c,d,e,f;
		in.useDelimiter("\\|");
		while (in.hasNext()) {
			a = in.next();
			b = in.next();
			c = in.next();
			d = in.next();
			e = in.next();
			f = in.next();
			if (a.equals("학부")) {
			try {
				p[idx] = new Under(a,b,c,d,e,f);	
			}catch (ArrayIndexOutOfBoundsException error) {
				p = Arrays.copyOf(p, p.length+1);
				p[idx] = new Under(a,b,c,d,e,f);
			}
				idx++;
			}
			else if (a.equals("석사")) {
				try {
					p[idx] = new MS(a,b,c,d,e,f);	
				}catch (ArrayIndexOutOfBoundsException error) {
					p = Arrays.copyOf(p, p.length+1);
					p[idx] = new MS(a,b,c,d,e,f);
				}
				idx++;
			}
			else if (a.equals("박사")) {
				try {
					p[idx] = new PhD(a,b,c,d,e,f);	
				}catch (ArrayIndexOutOfBoundsException error) {
					p = Arrays.copyOf(p, p.length+1);
					p[idx] = new PhD(a,b,c,d,e,f);
				}
				idx++;
			}
			else if (a.equals("조교")) {
				try {
					p[idx] = new Assistant(a,b,c,d,e,f);	
				}catch (ArrayIndexOutOfBoundsException error) {
					p = Arrays.copyOf(p, p.length+1);
					p[idx] = new Assistant(a,b,c,d,e,f);
				}
				idx++;
			}	
			else if (a.equals("교수")) {
				try {
					p[idx] = new Professor(a,b,c,d,e,f);	
				}catch (ArrayIndexOutOfBoundsException error) {
					p = Arrays.copyOf(p, p.length+1);
					p[idx] = new Professor(a,b,c,d,e,f);
				}
				idx++;
			}				
			in.nextLine();          
		}
		if (args[1].equals("print")) {
			int l = args[2].length();
			switch (args[2]) {
			case "all":
				Person.PrintAll(p);
				break;
			case "student":
				Person.PrintStudent(p);
				break;
			case "staff":
				Person.PrintStaff(p);
				break;
			default:
				if (l == 14) {
					Person.PrintNo(p, args[2]);
				}else {
					System.out.println("입력오류! 확인바람!");
				}
				break;
			}
		}else if (args[1].equals("sort")) {
			switch (args[2]) {
			case "ssno":
				Sort.Sorting(p, args[2]);
				Person.PrintAll(p);
				break;
			case "name":
				Sort.Sorting(p, args[2]);
				Person.PrintAll(p);
				break;
			case "birthdate":
				Sort.Sorting(p, args[2]);
				Person.PrintAll(p);
				break;
			case "address":
				Sort.Sorting(p, args[2]);
				Person.PrintAll(p);
				break;
			default:
				System.out.println("입력오류! 확인바람!");
				break;
			}
		}
	}
}

abstract class Person{
	String	type, ssno, name, birth, phone, address;
	public Person( String t, String n, String na, String b, String p, String a) {
		type = t; ssno = n; name = na; birth = b; phone = p; address = a;
	}
	public String toString() {
		return String.format("%s(%s) 생일:%s 전화:%s 주소:%s", 
											name, ssno, birth, phone, address);
	}
	public static void PrintAll(Person[] p) {
		for(Person P : p) {
			System.out.println(P);
		}
	}
	public static void PrintNo(Person[] p, String no) {
		for(Person P : p) {
			if (P.ssno.equals(no)) {
				System.out.println(P);
			}
		}
	}
	public static void PrintStudent(Person[] p) {
		for(Person P : p) {
			if (P.type.equals("학부") || P.type.equals("석사") || P.type.equals("박사")) {
				System.out.println(P);
			}
		}
	}
	public static void PrintStaff(Person[] p) {
		for(Person P : p) {
			if (P.type.equals("조교") || P.type.equals("교수")) {
				System.out.println(P);
			}
		}
	}
}

class Student extends Person{
	int adyear;
	String[] department = {"소프트웨어학과", "전기공학과", "자동차공학과", "전자공학과", "기계공학과"};
	
	public Student(String t, String n, String na, String b, String p, String a) {
		super(t,n,na,b,p,a);
		Setad(b);
	}
	public void Setad(String birth) {
		String[] bb = birth.split("-");
		int by = Integer.parseInt(bb[0]);
		adyear = by + 19;
	}
	public String toString() {
		return String.format("%s",super.toString());
	}
}

class Under extends Student{
	int acyear = Rand.r(1, 4);
	String[] circles = {"없음","기타동아리","탁구동아리","농구동아리","게임동아리","봉사동아리"};
	
	public Under(String t, String n, String na, String b, String p, String a) {
		super(t,n,na,b,p,a);
	}
	public String toString() {
		return String.format("[%s] [입학년도:%d] %-6s [%d학년] 동아리:%s %s",type,adyear, department[Rand.r(0, 4)], acyear, circles[Rand.r(0, 5)],super.toString());
	}
}

class Graduate extends Student{
	String[] rearea = {"네트워크","정보보안","데이터베이스","인공지능","소프트웨어","시스템","그래픽","로봇","과학연산","알고리즘"};
	
	public Graduate(String t, String n, String na, String b, String p, String a) {
		super(t,n,na,b,p,a);
	}
	public String toString() {
		return String.format("%s",super.toString());
	}
}

class MS extends Graduate{
	int semester = Rand.r(1, 4);
	
	public MS(String t, String n, String na, String b, String p, String a) {
		super(t,n,na,b,p,a);
	}
	public String toString() {
		return String.format("[%s] [입학년도:%d] %-6s [%d학기] 연구분야:%s %s",type,adyear, department[Rand.r(0, 4)],semester, rearea[Rand.r(0, 9)],super.toString());
	}
}

class PhD extends Graduate{
	int year = Rand.r(1, 6);
	
	public PhD(String t, String n, String na, String b, String p, String a) {
		super(t,n,na,b,p,a);
	}
	public String toString() {
		return String.format("[%s] [입학년도:%d] %-6s [%d년차] 연구분야:%s %s",type, adyear, department[Rand.r(0, 4)],year, rearea[Rand.r(0, 9)],super.toString());
	}
}

class Employee extends Person{
	String[] dename = {"소프트웨어학과", "전기공학과", "자동차공학과", "전자공학과", "기계공학과"};
	
	public Employee(String t, String n, String na, String b, String p, String a) {
		super(t,n,na,b,p,a);
	}
	public String toString() {
		return String.format("%s",super.toString());
	}
}

class Assistant extends Employee{
	String[] category = {"전임","근로","시간제"};
	
	public Assistant(String t, String n, String na, String b, String p, String a) {
		super(t,n,na,b,p,a);
	}
	public String toString() {
		return String.format("[%s] [부서명:%s] 구분:%s %s",type,dename[Rand.r(0, 4)],category[Rand.r(0, 2)],super.toString());
	}
}

class Professor extends Employee{
	String[] starea = {"네트워크","정보보안","데이터베이스","인공지능","소프트웨어","시스템","그래픽","로봇","과학연산","알고리즘"};
	String[][] location = {{"1호관","2호관","3호관","4호관","5호관","6호관","7호관","8호관"},
							{"101호","202호","303호","304호","405호","512호","626호","628호"}};
	
	public Professor(String t, String n, String na, String b, String p, String a) {
		super(t,n,na,b,p,a);
	}
	public String toString() {
		return String.format("[%s] [부서명:%s] 전공분야:%s [연구실 위치:%s %s] %s",type,dename[Rand.r(0, 4)],starea[Rand.r(0, 9)],location[0][Rand.r(0, 7)],location[1][Rand.r(0, 7)],super.toString());
	}
}

class Rand {
	public static int r(int min, int max)  {
		int range = (max - min) + 1;     
		return (int)(Math.random() * range) + min;
	}
}

class Sort {	
	public static void Sorting(Person[] n, String base) {
		Person tmp;
		String[] V1,V2;
		String v1,v2;
		double a, b;
		for (int j = 0; j< n.length-1;j++) {
			for(int i=0;i < n.length-1;i++) {
				if(base.equals("ssno")){
					V1 = n[i].ssno.split("-");
					v1 = V1[0]+V1[1];
					V2 = n[i+1].ssno.split("-");
					v2 = V2[0]+V2[1];
					a = Double.parseDouble(v1);
					b = Double.parseDouble(v2);
					if( a > b ) {
						tmp = n[i];
						n[i] = n[i+1];
						n[i+1] = tmp;
					}
				}else if(base.equals("name")){
					if(n[i].name.compareTo(n[i+1].name) > 0) {
						tmp = n[i];
						n[i] = n[i+1];
						n[i+1] = tmp;
					}
				}else if(base.equals("birthdate")){
					V1 = n[i].ssno.split("-");
					v1 = V1[0]+V1[1];
					V2 = n[i+1].ssno.split("-");
					v2 = V2[0]+V2[1];
					a = Double.parseDouble(v1);
					b = Double.parseDouble(v2);
					if(a > b) {
						tmp = n[i];
						n[i] = n[i+1];
						n[i+1] = tmp;
					}
				}else if(base.equals("address")){
					if(n[i].address.compareTo(n[i+1].address) > 0) {
						tmp = n[i];
						n[i] = n[i+1];
						n[i+1] = tmp;
					}
				}
			}
		}
	}
}
