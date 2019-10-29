package ajude.classesAuxiliares;

public class FormataURL {
    static String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
    static String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";
    static char[] tabela;
    
    static String pontuacao = "[,.?!;:/{}()*+$#@%-]";
    
    static {
        tabela = new char[256];
        for (int i = 0; i < tabela.length; ++i) {
	    tabela [i] = (char) i;
        }
        for (int i = 0; i < acentuado.length(); ++i) {
            tabela [acentuado.charAt(i)] = semAcento.charAt(i);
        }
    }
    
    public static String remover (final String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt (i);
            if (ch < 256) { 
                sb.append (tabela [ch]);
            } else {
                sb.append (ch);
            }
        }
        return sb.toString();
    }
    
    public static String formataURL(String s) {
    	s = remover(s);
    	s = s.toLowerCase();
    	s = s.replaceAll(pontuacao, " ");
    	s = s.replaceAll(" +", "-");
    	return s;
    }
    
}