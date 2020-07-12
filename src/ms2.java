import java.util.ArrayList;

public class ms2 {
    public int count;
    public ArrayList<Double> MC;
    public ArrayList<Double> MS;

    public ms2(){
        //ms2<=(c1+s4-c2-c3)/2
        MC=new ArrayList<>();
        MS=new ArrayList<>();
        MS.add(0.0); //ms0
        MS.add(0.0); //ms1
        MS.add(0.0); //ms2
        MS.add(0.0); //ms3
        MS.add(1.0); //ms4
        MC.add(0.0); //mc0
        MC.add(1.0); //mc1
        MC.add(-1.0); //mc2
        MC.add(-1.0); //mc3
        count=1;     // 2^-1
    }

    public void split(int n){
        for(; n>0;n--){
            // adjust list length
            MS.add(0.0);
            MS.add(0.0);
            MC.add(0.0);
            MC.add(0.0);
            MS.set(2,MS.get(2)*2);  //update ms2 for changed denominator

            int i=1;
            for(;i<MC.size();++i){
                MC.set(i,MC.get(i)*2);
            }
            i=3;
            while(i<MS.size()){
                if(MS.get(i)<0.3) ++i;
                else {
                    MS.set(i - 1, MS.get(i - 1) + MS.get(i));
                    MS.set(i + 2, MS.get(i + 2) + MS.get(i));
                    MC.set(i - 1, MC.get(i - 1) + MS.get(i));
                    MC.set(i, MC.get(i) - MS.get(i));
                    MC.set(i + 1, MC.get(i + 1) - MS.get(i));
                    MS.set(i, 0.0);
                    i+=3;
                }
            }
            ++count; //change denominator
        }
    }

    public void print(){
        System.out.print("( ");
        System.out.print(MC.get(1));
        for(int i=2;i<MC.size();++i){
            if(MC.get(i)>0) System.out.print("+");
            System.out.print(MC.get(i)+"MC"+i);
        }
        System.out.println();
        for(int i=1;i<MS.size();++i){
            if(MS.get(i)>0){
                System.out.print("+" + MS.get(i) + "MS"+i);
            }
        }
        System.out.print(" ) / 2^"+count+'\n');
    }

    public double calcMS2(){
        double denominator=Math.pow(2,count);
        for (int i=1;i<MC.size();++i) {
            if(MC.get(i)>denominator) {
                System.out.print("MC goes over 1");
                return 0.0;
            }
        }
        double max=0;
        for(int i=3;i<MS.size();++i){
            max=Math.max(MS.get(i),max);
        }
        int de=(int)denominator-MS.get(2).intValue();
        int nu=(int)denominator/2+(int)max;
        System.out.print("current bound for ms2: "+ nu/gcd(de,nu) + "/" + de/gcd(de,nu) +'n'+'\n');
        return (denominator/2+max)/(denominator-MS.get(2));
    }

    private int gcd(int a, int b)
    {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public static void main(String[] args) {
        ms2 a=new ms2();
        a.split(899);
        a.print();
        System.out.print(a.calcMS2());
    }
}
