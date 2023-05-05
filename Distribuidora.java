
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Distribuidora {

    public static void main(String[] args) {

        double valorTotal = 0;
        double maiorValor = 0;
        double menorValor = 0;
        double media = 0;
        int diasZerados = 0;
        int diasAcima = 0;
        int tamanhoArrays = 0;
        double[] valoresPositivos = new double [0];

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
// onde está indicado, o endereço onde a lista está salva ser colocada;
            Document doc = builder.parse("LOCAL DA LISTA");

            NodeList listaRows = doc.getElementsByTagName("row");

            int tamanhoDaLista = listaRows.getLength();

            for (int i = 0; i < tamanhoDaLista; i++){
                Node noRow = listaRows.item(i);

                if(noRow.getNodeType() == Node.ELEMENT_NODE){

                    Element elementRow = (Element) noRow;

                    NodeList listaDiasEValores = elementRow.getChildNodes();

                    int tamanhoListaDEV = listaDiasEValores.getLength();

                    for (int j =0; j < tamanhoListaDEV; j++){
                        Node noFilho = listaDiasEValores.item(j);
                        if(noFilho.getNodeType() == Node.ELEMENT_NODE){
                            Element diasValores = (Element) noFilho;
                            switch(diasValores.getTagName()){

                                case "dia":
                                    break;
                                case "valor":
                                    String valor = diasValores.getTextContent();
                                    double valorDouble = Double.parseDouble(valor);
                                    if(valorDouble > 0){
                                        int tamanhoAntigo = tamanhoArrays;
                                        tamanhoArrays = tamanhoArrays + 1;
                                        double[] novoArray = new double[tamanhoArrays];
                                        for(int k=0; k < tamanhoAntigo; k++){
                                            novoArray[k] = valoresPositivos[k];
                                        }
                                        valoresPositivos = novoArray;
                                        valoresPositivos[tamanhoArrays-1] = valorDouble;
                                        valorTotal = valorTotal + valorDouble;
                                    } else {
                                        diasZerados = diasZerados + 1;
                                    }
                                    break;
                                case "row":
                                    break;
                            }
                        }
                    }
                }
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Distribuidora.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex){
            Logger.getLogger(Distribuidora.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(Distribuidora.class.getName()).log(Level.SEVERE, null, ex);
        }

        media = valorTotal / tamanhoArrays;

        for(int u=0; u < tamanhoArrays; u++){
            if(u == 0){
                maiorValor = valoresPositivos[u];
                menorValor = valoresPositivos[u];
                if(valoresPositivos[u] > media){
                    diasAcima = diasAcima + 1;
                }
            } else{
                if(valoresPositivos[u] > maiorValor){
                    maiorValor = valoresPositivos[u];
                    if(valoresPositivos[u] > media){
                        diasAcima = diasAcima + 1;
                    }
                } else if (valoresPositivos[u] < menorValor){
                    menorValor = valoresPositivos[u];
                } else{
                    if(valoresPositivos[u] > media){
                        diasAcima = diasAcima + 1;
                    }
                }
            }
        }

        System.out.println("Maior faturamento em um dia: " + maiorValor);
        System.out.println("Menor faturamento em um dia (sem contar dias zerados): " + menorValor);
        System.out.println("Número de dias acima da média (excluindo os dias zerados): " + diasAcima);

    }

}
