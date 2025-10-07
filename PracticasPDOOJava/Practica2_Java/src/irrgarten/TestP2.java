/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author joaquin
 */
public class TestP2 {
    public static void main(String[] args) {
        System.out.println("=== Enumerados ===");
        for (Directions d : Directions.values()) System.out.print(d + " ");
        System.out.println();
        for (Orientation o : Orientation.values()) System.out.print(o + " ");
        System.out.println();
        for (GameCharacter gc : GameCharacter.values()) System.out.print(gc + " ");
        System.out.println("\n");
        
            System.out.println("=== Weapons & Shield ===");
            Weapon w1 = new Weapon(2.0f, 5);
            Weapon w2 = new Weapon(1.5f, 0);
            Shield s1 = new Shield(3.0f, 4);
            Shield s2 = new Shield(1.0f, 1);
            
            System.out.println(w1);
            System.out.println(w2);
            System.out.println(s1);
            System.out.println(s2);
            
            System.out.println("w1.attack --> " + w1.attack());
            System.out.println("w2.attack --> " + w2.attack());
            System.out.println("s1.defend --> " + s1.protect());
            System.out.println("s2.defend --> " + s2.protect());
            System.out.println("s2.defend --> " + s2.protect());
            
            System.out.println("\n === Dice (100 iteraciones) ===");
            final int N = 100;
            
            int[] posCount = new int[10];
            for (int i = 0; i < N; i++)  posCount[Dice.randomPos(10)]++;
            System.out.print("randomPos(10): ");
            for (int i = 0; i < posCount.length; i++) System.out.print(i + " : " + posCount[i] + " ");
            System.out.println();
            
            int[] who = new int[3];
            for (int i = 0; i < N; i++) who[Dice.whoStarts(3)]++;
            System.out.println("whoStarts(3): 0 = " + who[0] + " 1 = " + who[1] + " 2 " + who[2]);
            
            int resurrects = 0;
            for (int i = 0; i < N; i++) if (Dice.resurrectPlayer()) resurrects++;
            System.out.println("resurrectPlayer ~30%: " + resurrects + " / " + N);
            
            int wrMin = 999, wrMax = 1;
            for (int i=0;i<N;i++){ int v=Dice.weaponsReward(); wrMin=Math.min(wrMin,v); wrMax=Math.max(wrMax,v); }
            System.out.println("weaponsReward rango: ["+wrMin+","+wrMax+"] (esperado [0,2])");

            int srMin=999, srMax=-1;
            for (int i=0;i<N;i++){ int v=Dice.shieldsReward(); srMin=Math.min(srMin,v); srMax=Math.max(srMax,v); }
            System.out.println("shieldsReward rango: ["+srMin+","+srMax+"] (esperado [0,3])");

            int hrMin=999, hrMax=-1;
            for (int i=0;i<N;i++){ int v=Dice.healthReward(); hrMin=Math.min(hrMin,v); hrMax=Math.max(hrMax,v); }
            System.out.println("healthReward rango: ["+hrMin+","+hrMax+"] (esperado [0,5])");

            float wpMin=999, wpMax=-1, spMin=999, spMax=-1;
            for (int i=0;i<N;i++){
                float wp = Dice.weaponPower();  wpMin=Math.min(wpMin,wp);  wpMax=Math.max(wpMax,wp);
                float sp = Dice.shieldPower();  spMin=Math.min(spMin,sp);  spMax=Math.max(spMax,sp);
            }
            System.out.printf(java.util.Locale.ROOT, "weaponPower [%.3f, %.3f) esperado [0,3)%n", wpMin, wpMax);
            System.out.printf(java.util.Locale.ROOT, "shieldPower [%.3f, %.3f) esperado [0,2)%n", spMin, spMax);

            int usesMin=999, usesMax=-1;
            for (int i=0;i<N;i++){ int v=Dice.usesLeft(); usesMin=Math.min(usesMin,v); usesMax=Math.max(usesMax,v); }
            System.out.println("usesLeft rango: ["+usesMin+","+usesMax+"] (esperado [0,5])");

            float intMin=999, intMax=-1;
            for (int i=0;i<N;i++){ float v=Dice.intensity(5.0f); intMin=Math.min(intMin,v); intMax=Math.max(intMax,v); }
            System.out.printf(java.util.Locale.ROOT, "intensity(5.0) [%.3f, %.3f) esperado [0,5)%n", intMin, intMax);

            for (int uses=0; uses<=5; uses++){
                int t=0;
                for (int i=0;i<N;i++) if (Dice.discardElement(uses)) t++;
                System.out.println("discardElement("+uses+") -> true aprox " + t + "/" + N);
            }

            System.out.println("\n=== GameState ===");
            GameState gs = new GameState(
                "Laberinto(5x5)",
                "P0(1,1), P1(3,2)",
                "M0(2,4)",
                0,
                false,
                "Comienza la partida"
            );
            System.out.println(gs);
            System.out.println("Jugador actual: " + gs.getCurrentPlayer());
            System.out.println("¿Hay ganador? " + gs.isWinner());
    }
}
