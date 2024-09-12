import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
* Classe Medalhista: representa um medalhista olímpico e sua coleção de medalhas
nas Olimpíadas de Paris 2024
*/
class Medalhista {
/** Para criar o vetor com no máximo 8 medalhas */
private static final int MAX_MEDALHAS = 8;
/** Nome do medalhista */
private String name;
/** Gênero do medalhista */
private String gender;
/** Data de nascimento do medalhista */
private LocalDate birthDate;
/** País do medalhista */
private String country;
/** Coleção de medalhas do medalhista */
private Medalha[] medals;
/** Contador de medalhas e índice para controlar o vetor de medalhas */
private int medalCount;
/**
* Cria um medalhista olímpico. Nenhum dado precisa ser validado.
* @param nome Nome do medalhista no formato "SOBRENONE nome"
* @param genero Gênero do medalhista
* @param nascimento Data de nascimento do medalhista
* @param pais País do medalhista (conforme dados originais, em inglês)
*/
public Medalhista(String nome, String genero, LocalDate nascimento, String
pais){
this.name = nome;
this.gender = genero;
this.birthDate = nascimento;
this.country = pais;
this.medals = new Medalha[MAX_MEDALHAS];
this.medalCount = 0;

}

/**
* Inclui uma medalha na coleção do medalhista. Retorna a quantidade atual de
medalhas do atleta.
* @param medalha A medalha a ser armazenada.
* @return A quantidade total de medalhas do atleta após a inclusão.
*/
public int incluirMedalha(Medalha medalha){
if (medalCount < MAX_MEDALHAS) {
    medals[medalCount] = medalha;
    medalCount ++;
}
return medalCount;
}

/**
* Total de medalhas do atleta. É um número maior ou igual a 0.
* @return Inteiro com o total de medalhas do atleta (>=0)
*/
public int totalMedalhas(){

return medalCount;
}

/**
* Retorna um relatório das medalhas do atleta conforme o tipo solicitado pelo
parâmetro. Veja no
* enunciado da atividade o formato correto deste relatório. Em caso de não
possuir medalhas
* deste tipo, a resposta deve ser "Nao possui medalha de TIPO".
* @param tipo Tipo da medalha para o relatório
* @return Uma string, multilinhas, com o relatório de medalhas daquele tipo.
Em caso de não possuir
* medalhas deste tipo, a resposta deve ser "Nao possui medalha de TIPO".
*/
public String relatorioDeMedalhas(TipoMedalha tipo){
    StringBuilder sb = new StringBuilder();
    boolean possuiMedalha = false;
    
    for (int i = 0; i < medalCount; i++){
        if (medals[i].getTipo() == tipo) {
            sb.append(medals[i].toString()).append("\n");
            possuiMedalha = true;
        }
    }

    if (!possuiMedalha) {
        sb.append("Nao possui medalha de").append(tipo).append("\n");
    
    }

return sb.toString();
}

/**
* Retorna o nome do país do medalhista (conforme arquivo original em inglês.)
* @return String contendo o nome do país do medalhista (conforme arquivo
original em inglês, iniciais em maiúsculas.)
*/
public String getPais(){
return country;
}
/**
* Retorna uma cópia da data de nascimento do medalhista.
* @return LocalDate com a data de nascimento do medalhista.
*/
public LocalDate getNascimento(){
return LocalDate.from(birthDate);
}

/**
* Deve retornar os dados pessoais do medalhista, sem as medalhas, conforme
especificado no enunciado
* da atividade.
* @return String de uma linha, com os dados do medalhista, sem dados da
medalha.
*/
public String toString(){

return String.format("%s, %s. Nascimento: %s. Pais: %s", name,gender.equalsIgnoreCase("FEMININO") ? "FEMININO" : "MASCULINO", birthDate.format(DateTimeFormatter.ofPattern("d/M/yyyy")), country);
}
}
/** Enumerador para medalhas de ouro, prata e bronze */
enum TipoMedalha {
OURO,
PRATA,
BRONZE
}

/**
* Representa uma medalha obtida nos Jogos Olímpicos de Paris em 2024.
*/
class Medalha {
/** Tipo/cor da medalha conforme o enumerador */
private TipoMedalha metalType;
/** Data de obtenção da medalha */
private LocalDate medalDate;
/** Disciplina da medalha, conforme arquivo de dados */
private String discipline;
/** Evento da medalha, conforme arquivo de dados */
private String event;
/** Cria uma medalha com os dados do parâmetro. Nenhum dado é validado */
public Medalha(TipoMedalha tipo, LocalDate data, String disciplina, String
evento){
metalType = tipo;
medalDate = data;
discipline = disciplina;
event = evento;
}

/**
* Retorna o tipo de medalha, conforme o enumerador
* @return TipoMedalha (enumerador) com o tipo/cor desta medalha
*/
public TipoMedalha getTipo(){
return metalType;
}

/**
* Retorna uma string com o "relatório" da medalha de acordo com o especificado
no enunciado do problema.
* Contém uma linha que já formata a data da medalha no formato brasileiro. O
restante deve ser implementado.
*/
public String toString(){
String dataFormatada =
DateTimeFormatter.ofPattern("dd/MM/YYYY").format(medalDate); //formata a data em DD/MM/AAAA

return String.format("%s - %s - %s - %s", metalType, discipline, event, dataFormatada);
}
}

public class App {
public static void main(String[] args) {
    String arquivoCSV = "C:/Users/1481326/Desktop/Verde/Medalhas-Olimpc/medallists.csv";
    List<Medalhista> medalhistas new ArrayList<>();
    try (BufferedReader arquivo = new BufferedReader(new InputStreamReader(new FileInputStream(arquivoCSV), "UTF-8"))){
        String linha;
        arquivo.readLine();

        while ((linha = arquivo.readLine()) != null){
            String[] campos = linha.split(",");
            String nome = campos[0];
            String genero = campos[1];
            LocalDate nascimento = LocalDate.parse(campos[2], DateTimeFormatter.ofPattern("d//m/yyyy"));
            String pais = campos[3];
            TipoMedalha tipoMedalha = TipoMedalha.valueOf(campos[4].toUpperCase());
            LocalDate dataMedalha = LocalDate.parse(campos[5], DateTimeFormatter.ofPattern("d//m/yyyy"));
            String disciplina = campos[6];
            String evento = campos[7];
            Medalhista medalhista = encontrarOuCriarMedalhista(medalhistas, nome, genero, nascimento, pais);
            Medalha medalha = new Medalha(tipoMedalha, dataMedalha, disciplina, evento);
            medalhista.incluirMedalha(medalha);
        }
    } (catch IOException e)
    { System.err.println("Erro ao ler o arquivo" + e.getMessage());
}
    Scanner scanner = new Scanner(System.in);
    String entrada;
    while (!(entrada = scanner.nextLine()).equals("FIM")) {
        String[] partes = entrada.split(" , ");
        String nomeAtleta = partes[0];
        TipoMedalha tipoConsulta = TipoMedalha.valueOf(partes[1].toUpperCase);
        Medalhista medalhista = buscarMedalhista(medalhistas, nomeAtleta);
        if (medalhista != null) {
            System.out.println(medalhista);
            System.out.println(medalhista.relatorioDeMedalhas(tipoConsulta));
        }else {
            System.out.println("Medalhista " + nomeAtleta + " nao encontrado");
        }
        System.out.println();
    }
}
