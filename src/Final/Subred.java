/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final;

/**
 *
 * @author Home
 */
public class Subred extends Red {
    private int cantidadSubredes;
    public Subred(String direccionIP, String mascara,int cantidadSubredes) {
        super(direccionIP, mascara);
        this.cantidadSubredes= cantidadSubredes;
    }

    public boolean verificarDireccionIP() {
        // Verificar si la dirección IP está correctamente escrita
        // Aquí puedes implementar la lógica de validación
        return (super.verificarIP()); // Cambiar esto por la lógica real
    }

    @Override
    public boolean verificarMascara() {
        // Verificar si la máscara de subred está correctamente escrita
        // Aquí puedes implementar la lógica de validación
        
        return (super.verificarMascara()); // Cambiar esto por la lógica real
    }
   
   
    public String direccionBroadcast(String direccionSubRed) {
        // Implementa la lógica para calcular la dirección de broadcast
        // Dividir la dirección de red en octetos
        String[] octetos = direccionSubRed.split("\\.");
    
        // Convertir los octetos a enteros
        int[] red = new int[4];
        for (int i = 0; i < 4; i++) {
            red[i] = Integer.parseInt(octetos[i]);
        }
    
        // Calcular la dirección de broadcast
        int[] broadcast = new int[4];
        broadcast[0] = red[0];
        broadcast[1] = red[1];
        broadcast[2] = red[2];
        broadcast[3] = red[3] + numeroDeHosts(); // Sumar numero de hosts para obtener la dirección de broadcast

        // Formatear la dirección de broadcast como una cadena y devolverla
        return String.format("%d.%d.%d.%d", broadcast[0], broadcast[1], broadcast[2], broadcast[3]);
        
    }

    
    @Override
    public int prefijoSubred() {
        // Implementa la lógica para calcular el prefijo
        return super.prefijoSubred(); 
        //le a;adimos bits, segun la cantidad de subredes que ponemos en el constrcutor
    }
   
    
    
    @Override
    public int numeroDeHosts() {
        // Implementa la lógica para calcular el número de hosts
        return super.numeroDeHosts()/cantidadSubredes; // Ejemplo, cambia esto por la lógica real
    }
    
    
    public String[] calcularSubredes(int cantidadDeSubredes) {
    int prefijo = prefijoSubred();
    int bitsSubred = 32 - prefijo;
    int bitsNuevasSubredes = (int) Math.ceil(Math.log(cantidadDeSubredes) / Math.log(2));
    if (bitsNuevasSubredes > bitsSubred) {
        throw new IllegalArgumentException("No se pueden crear tantas subredes con la máscara de subred actual.");
    }
    int tamañoNuevaSubred = (int) Math.pow(2, bitsSubred - bitsNuevasSubredes);
    String[] subredes = new String[cantidadDeSubredes];

    // Obtener los octetos de la dirección de red original
    String[] octetosRed = direccionRed().split("\\.");

    for (int i = 0; i < cantidadDeSubredes; i++) {
        // Calcular los nuevos octetos de la dirección de la subred
        int cuartoOcteto = Integer.parseInt(octetosRed[3]) + i * tamañoNuevaSubred;
        int tercerOcteto = Integer.parseInt(octetosRed[2]);
        int segundoOcteto = Integer.parseInt(octetosRed[1]);
        int primerOcteto = Integer.parseInt(octetosRed[0]);

        // Ajustar los octetos si se supera el límite
        if (cuartoOcteto > 255) {
            tercerOcteto += cuartoOcteto / 256;
            cuartoOcteto %= 256;
            if (tercerOcteto > 255) {
                segundoOcteto += tercerOcteto / 256;
                tercerOcteto %= 256;
                if (segundoOcteto > 255) {
                    primerOcteto += segundoOcteto / 256;
                    segundoOcteto %= 256;
                }
            }
        }

        // Construir la nueva dirección de subred y almacenarla en el arreglo
        subredes[i] = primerOcteto + "." + segundoOcteto + "." + tercerOcteto + "." + cuartoOcteto;
    }

    return subredes;
}

 
 public String MascaraSubred() {
    // Validar entradas
    
    return null;
}





          
}



