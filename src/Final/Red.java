/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final;

/**
 *
 * @author Home
 */
public class Red {
    protected String direccionIp;
    protected String mascaraSubred;

    public Red(String direccionIp, String mascaraSubred) {
        setDireccionIp(direccionIp);
        setMascaraSubred(mascaraSubred);
    }

    public String binarioIP() {
        return convertirABinario(direccionIp);
    }

    public String binarioMascara() {
        return convertirABinario(mascaraSubred);
    }

    private String convertirABinario(String direccion) {
        String[] octetos = direccion.split("\\.");
        StringBuilder binario = new StringBuilder();
        for (String octeto : octetos) {
            try {
                String binOcteto = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octeto))).replace(' ', '0');
                binario.append(binOcteto).append(".");
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el octeto a binario: " + e.getMessage());
                return null;
            }
        }
        return binario.toString().substring(0, binario.length() - 1);
    }

    public String determinarClase() {
        try {
            int primerOcteto = Integer.parseInt(direccionIp.split("\\.")[0]);
            if (primerOcteto >= 1 && primerOcteto <= 126) {
                return "A";
            } else if (primerOcteto >= 128 && primerOcteto <= 191) {
                return "B";
            } else if (primerOcteto >= 192 && primerOcteto <= 223) {
                return "C";
            } else if (primerOcteto >= 224 && primerOcteto <= 239) {
                return "D";
            } else {
                return "E";
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al determinar la clase de la IP: " + e.getMessage());
            return null;
        }
    }

    public int prefijoSubred() {
        String[] octetos = mascaraSubred.split("\\.");
        int prefijo = 0;
        for (String octeto : octetos) {
            int valor = Integer.parseInt(octeto);
            while (valor > 0) {
                prefijo += valor & 1;
                valor >>= 1;
            }
        }
        return prefijo;
    }

    public String direccionRed() {
        String[] ip = direccionIp.split("\\.");
        String[] mascara = mascaraSubred.split("\\.");
        StringBuilder direccionRed = new StringBuilder();
        try {
            for (int i = 0; i < 4; i++) {
                int octetoRed = Integer.parseInt(ip[i]) & Integer.parseInt(mascara[i]);
                direccionRed.append(octetoRed).append(".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al calcular la dirección de red: " + e.getMessage());
            return null;
        }
        return direccionRed.toString().substring(0, direccionRed.length() - 1);
    }

    public String direccionBroadcast() {
        String[] ip = direccionIp.split("\\.");
        String[] mascara = mascaraSubred.split("\\.");
        StringBuilder direccionBroadcast = new StringBuilder();
        try {
            for (int i = 0; i < 4; i++) {
                int octetoBroadcast = Integer.parseInt(ip[i]) | (~Integer.parseInt(mascara[i]) & 0xFF);
                direccionBroadcast.append(octetoBroadcast).append(".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al calcular la dirección de broadcast: " + e.getMessage());
            return null;
        }
        return direccionBroadcast.toString().substring(0, direccionBroadcast.length() - 1);
    }

    public int numeroDeHosts() {
        try {
            return (int) Math.pow(2, 32 - prefijoSubred()) - 2;
        } catch (Exception e) {
            System.out.println("Error al calcular el número total de hosts: " + e.getMessage());
            return -1;
        }
    }

    public boolean verificarIP() {
        String[] octetos = direccionIp.split("\\.");
        if (octetos.length != 4) {
            return false;
        }
        for (String octeto : octetos) {
            int valor;
            try {
                valor = Integer.parseInt(octeto);
            } catch (NumberFormatException e) {
                System.out.println("Error al verificar la IP: " + e.getMessage());
                return false;
            }
            if (valor < 0 || valor > 255) {
                return false;
            }
        }
        return true;
    }

    public boolean verificarMascara() {
        String[] octetos = mascaraSubred.split("\\.");
        if (octetos.length != 4) {
            return false;
        }
        StringBuilder binarioMascara = new StringBuilder();
        try {
            for (String octeto : octetos) {
                int valor = Integer.parseInt(octeto);
                binarioMascara.append(String.format("%8s", Integer.toBinaryString(valor)).replace(' ', '0'));
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al verificar la máscara de subred: " + e.getMessage());
            return false;
        }
        int primeraCero = binarioMascara.indexOf("0");
        int ultimaUno = binarioMascara.lastIndexOf("1");
        return primeraCero == -1 || ultimaUno < primeraCero;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public String getMascaraSubred() {
        return mascaraSubred;
    }

    public void setDireccionIp(String direccionIp) {
        String[] octetos = direccionIp.split("\\.");
        if (octetos.length != 4) {
            throw new IllegalArgumentException("La dirección IP debe tener 4 octetos.");
        }
        for (String octeto : octetos) {
            int valor = Integer.parseInt(octeto);
            if (valor < 0 || valor > 255) {
                throw new IllegalArgumentException("Valor de octeto inválido: " + valor);
            }
        }
        this.direccionIp = direccionIp;
    }

    public void setMascaraSubred(String mascaraSubred) {
        String[] octetos = mascaraSubred.split("\\.");
        if (octetos.length != 4) {
            throw new IllegalArgumentException("La máscara de subred debe tener 4 octetos.");
        }
        StringBuilder binarioMascara = new StringBuilder();
        for (String octeto : octetos) {
            int valor = Integer.parseInt(octeto);
            if (valor < 0 || valor > 255) {
                throw new IllegalArgumentException("Valor de octeto inválido en la máscara de subred: " + valor);
            }
            binarioMascara.append(String.format("%8s", Integer.toBinaryString(valor)).replace(' ', '0'));
        }
        int primeraCero = binarioMascara.indexOf("0");
        int ultimaUno = binarioMascara.lastIndexOf("1");
        if (primeraCero != -1 && ultimaUno >= primeraCero) {
            throw new IllegalArgumentException("La máscara de subred no es válida.");
        }
        this.mascaraSubred = mascaraSubred;
    }
    
   
    
    
    
    
    
    
    
    }