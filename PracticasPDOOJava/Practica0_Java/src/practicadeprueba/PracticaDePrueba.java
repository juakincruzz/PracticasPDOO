
package practicadeprueba;
/**
 *
 * @author cmcruz
 */
class Errores{
    private int [] numeros;
        
    Errores(){
        numeros = new int[5];
    }     
    void Prueba(){
         for (int i=0; i<=numeros.length - 1; i++){
             numeros[i] = i * 2;
         }
         if (numeros[2] == 4) {
             System.out.println("El valor en la posicion 2 es igual a 4");
         } else {
             System.out.println("El valor en la posicion 2 NO es 4");
         }


         System.out.println("El ultimo numero del array es: " + numeros[numeros.length - 1]);
    }

    public void Prueba2() {         
            numeros = new int []{-5, -3, -10, -1, -4};
            int mayor = numeros[0];
            for (int i = 1; i < numeros.length; i++) {
                if (numeros[i] > mayor) { 
                    mayor = numeros[i];
                }
            }
            if (mayor < 0) { 
                System.out.println("El mayor numero es negativo");
            }
            System.out.println("El mayor numero es:"+ mayor);
    }
}

public class PracticaDePrueba {
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hola mundo");
        Errores error = new Errores();
        
        error.Prueba();
        error.Prueba2();
    }    
}
