import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuscadorFuga {
    public static void main(String [] args){
        //VARIABLES
        ListaPersonas personas = new ListaPersonas();
        String coincidenias = new String();
        String nombres = new String();
        //String patron1 = "gmail";
        //String patron2 = "yahoo";
        ArrayList<String> patrones = new ArrayList<>();
        patrones.add("gmail");
        patrones.add("yahoo");
        boolean comp = false;
        boolean comp1 = false;
        boolean comp2 = false;
        int cont = 0;

        //Para leer el archivo
        try{
            File doc = new File("D:\\LeakedData.txt");
            BufferedReader obj = new BufferedReader(new FileReader(doc));
            String linea;
            while ((linea = obj.readLine()) != null) {
                //System.out.println(linea);
                //System.out.println(fuerzaBruta(linea,"AV."));
                String [] campos_obj={};
                campos_obj=linea.split(",");
                //System.out.println(campos_obj.length);
                Persona persona = new Persona();
                for(int i = 0;i<campos_obj.length;i++){
                    persona.setDatos(campos_obj);
                }
                //Se añade los datos del archivo al array
                personas.addPersona(persona);
                //cont++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }

        for(String patron : patrones){
            System.out.println("");
            System.out.println("---Patron a buscar " + patron);
            System.out.println("");
            cont=0; //Se reinician el contador
            nombres = ""; //se borran los nombres


            //TODO FUERZA BRUTA
            long tiempoInicio = System.nanoTime();
            for(int j = 0; j<personas.tamanio();j++) {
                comp = fuerzaBruta1(personas.getPersonas().get(j).getDatoCorreo(), patron);
                if(comp){
                    nombres = nombres + personas.getPersonas().get(j).getDatoNombre() + " "+
                            personas.getPersonas().get(j).getDatoApellido() + " , ";
                    cont++;
                }
            }
            long tiempoFinal = System.nanoTime();
            System.out.println("ALGORITMO FUERZA BRUTA");
            System.out.println("Tiempo utilizado en (ns): " + (tiempoFinal-tiempoInicio));
            System.out.println("El número de coincidencias encontradas con el patron " +  patron  + " son: " + cont);
            System.out.println("Los nombres encontrados con el patron " + patron + " son: ");
            if(nombres.length() !=0 ){
                System.out.println(nombres.substring(0,nombres.length()-2));
            }else{
                System.out.println("No existen registros con el patron: " + patron);
            }
            cont=0; //Se reinician el contador
            nombres = ""; //se borran los nombres


            //TODO: CON KMP
            System.out.println();
            System.out.println("ALGORITMO KMP");
            long tiempoInicio1 = System.nanoTime();

            for(int j = 0; j<personas.tamanio();j++) {
                comp1 = findKMP(personas.getPersonas().get(j).getDatoCorreo().toCharArray(), patron.toCharArray());
                if(comp1){
                    nombres = nombres + personas.getPersonas().get(j).getDatoNombre() + " "+
                            personas.getPersonas().get(j).getDatoApellido() + " , ";
                    cont++;
                }
            }
            long tiempoFinal1 = System.nanoTime();
            System.out.println("Tiempo utilizado en (ns): " + (tiempoFinal1-tiempoInicio1));
            System.out.println("El número de coincidencias encontradas con el patron " +  patron  + " son: " + cont);
            System.out.println("Los nombres encontrados con el patron " + patron + " son: ");
            if(nombres.length() !=0 ){
                System.out.println(nombres.substring(0,nombres.length()-2));
            }else{
                System.out.println("No existen registros con el patron: " + patron);
            }
            cont=0; //Se reinician el contador
            nombres = ""; //se borran los nombres


            //TODO CON BOYER MOORE
            System.out.println("");
            System.out.println("ALGORITMO BOYER MOORE");
            long tiempoInicio2 = System.nanoTime();
            for(int j = 0; j<personas.tamanio();j++) {
                comp2 = busquedaBoyerMoore(personas.getPersonas().get(j).getDatoCorreo().toCharArray(), patron.toCharArray());
                if(comp2){
                    nombres = nombres + personas.getPersonas().get(j).getDatoNombre() + " "+
                            personas.getPersonas().get(j).getDatoApellido() + " , ";
                    cont++;
                }
            }
            long tiempoFinal2 = System.nanoTime();
            System.out.println("Tiempo utilizado en (ns): " + (tiempoFinal2-tiempoInicio2));
            System.out.println("El número de coincidencias encontradas con el patron " +  patron  + " son: " + cont);
            System.out.println("Los nombres encontrados con el patron " + patron + " son: ");
            if(nombres.length() !=0 ){
                System.out.println(nombres.substring(0,nombres.length()-2));
            }else{
                System.out.println("No existen registros con el patron: " + patron);
            }

        }
    }

    //TODO ****************************** FUERZA BRUTA **************************************************************
    private static Boolean fuerzaBruta1(String texto, String patron) {
        int longitudTexto = texto.length();
        int longitudPatron = patron.length();
        int k = -1;
        String strPosiciones = "En String Se encontraron los siguientes indices: ";
        boolean comprobacion = false;
        for (int i = 0; i <= (longitudTexto - longitudPatron); i++) { //iterar sober todo el texto
            int j = 0; //iterar el patron
            //System.out.println(texto.charAt(i+j));
            //System.out.println(patron.charAt(j));
            while (j < longitudPatron && texto.charAt(i+j) == patron.charAt(j)) {
                //System.out.println(j);
                j++;
            }
            if (j == longitudPatron) {
                //strPosiciones = strPosiciones + i + " hasta " + (i + longitudPatron) + " , ";
                //return i;
                k=1;
                break;
            }
        }
        if(k==1){
            return true;
        }else {
            return false;
        }
        //return strPosiciones.substring(0, strPosiciones.length() - 2);
        //return strPosiciones;
    }

    //TODO *********************************** KMP***********************************************************
    public static Boolean findKMP(char[] text, char[] pattern) {
        int n = text.length;
        int m = pattern.length;
        boolean comprobar = false;
        int h = -1;
        String strResultados = "";
        if (m == 0)
            //return strResultados; // trivial search for empty string
            return comprobar=false;
        int[] fail = tablaFalloKMP(pattern); // computed by private utility
        //System.out.println(Arrays.toString(fail)); // Para visualizar el contenido de la funci�n de fallo.
        int j = 0; // index into text
        int k = 0; // index into pattern
        while (j < n) {
            if (text[j] == pattern[k]) { // pattern[0..k] matched thus far
                if (k == m - 1) {
                    strResultados += (j - m + 1) + ","; // match is complete
                    h=1;
                    k = 0;
                }
                j++; // otherwise, try to extend match
                k++;
            } else if (k > 0)
                k = fail[k - 1]; // reuse suffix of P[0..k-1]
            else
                j++;
        }
        if(h==1){
            return true;
        }else {
            return false;
        }

        //return strResultados; // reached end without match
    }

    private static int[] tablaFalloKMP(char[] entrada) {
        int longitudVector = entrada.length;
        int[] tablaFallo = new int[longitudVector]; // Mismo tama�o que el patr�n
        int a = 1;
        int b = 0;
        while (a < longitudVector) { // compute fail[j] during this pass, if nonzero
            if (entrada[a] == entrada[b]) { // k + 1 characters match thus far
                tablaFallo[a] = b + 1;
                a++;
                b++;
            } else if (b > 0) // k follows a matching prefix
                b = tablaFallo[b - 1];
            else // no match found starting at j
                a++;
        }
        // System.out.println(fail);
        return tablaFallo;
    }


    //TODO ****************************** BOYER MOORE **************************************************************
    public static Boolean busquedaBoyerMoore(char [] texto, char[] patron){
        int posicionCoincidencia = 0;
        int h =-1;
        boolean comprobar = false;

        //longitudes del patron y del texto
        int n = texto.length;
        int m = patron.length;

        if(m==0)
            return comprobar=false;
        //tabla D1
        Map<Character,Integer> vectorCaracteres = new HashMap<>();
        for(int i =0; i<n; i++)
            vectorCaracteres.put(texto[i],-1);

        for(int cont = 0; cont<m; cont++)
            vectorCaracteres.put(patron[cont],cont);

        int j=m-1;
        int k=m-1;

        while(j<n){
            if(texto[j]==patron[k]){
                if(k==0){
                    posicionCoincidencia=j;
                    h=1;
                    return true;

                }
                j--;
                k--;
            }else{
                j += m - Math.min(k,1+vectorCaracteres.get(texto[j]));
                k=m-1;
            }
        }
        if(h==1){
            return true;
        }else{
            return false;
        }
        //return  posicionCoincidencia;
    }

}
