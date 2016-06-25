package gerenciadores;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class Digital extends Atividade{

	private String local;
	public static String nome;
	private int horarioInicio;
	private int horarioTermino;
	private int dataDia;
	private int dataMes;
	private int qtdAlunos;
	Scanner sc = new Scanner(System.in);
	public static Map<String, Digital> AtividadesConstruidas = new LinkedHashMap<String, Digital>();

	public Map<String, Digital> getAtividadesConstruidas() {
		return AtividadesConstruidas;
	}
	
	public void addToAtividade(){
		Iterator<Digital> it = AtividadesConstruidas.values().iterator();
		while(it.hasNext()){
			Digital atual = it.next();
			super.AtividadesConstruidas.putIfAbsent(atual.getNome(), atual);
		}
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setNome(String name){
		nome = name;
	}
	
	public void setLocal() {
		boolean ok = false;
		Local l = new Local();
		String local;
		while (ok == false) {
			System.out.println("Nome do Local: ");
			local = sc.next();
			local = local.toLowerCase();
			ok = l.checkLocal(local);
			if (ok == true) {
				l.setLocal(local);
				this.local = local;
				return;
			}
			System.out.println("Tente novamente");
			System.out.println("Sala de atividades ou quadra");
		}
	}

	public void setHorario() {
		Horario h = new Horario();
		boolean ok = false;
		int horarioInicio = -1;
		int horarioTermino = -1;
		while (ok == false) {
			System.out
					.println("\nInsira os horarios de inicio e termino (maximo 2 horas)");
			System.out.println("Horario de Inicio: ");
			horarioInicio = sc.nextInt();
			ok = h.checkHora(horarioInicio);
			if (ok == true) {
				this.horarioInicio = horarioInicio;
				ok = false;
				while (ok == false) {
					System.out.println("Horario de Termino: ");
					horarioTermino = sc.nextInt();
					ok = h.checkHora(horarioTermino);
					ok = false;
					ok = h.checkduracao(horarioInicio,horarioTermino);
				}
				this.horarioTermino = horarioTermino;
			}
		}
	}

	public void setData() {
		int dataMes = -1;
		int dataDia = -1;
		boolean ok = false;
		Mes m = new Mes();
		DiaDe d = new DiaDe();
		while (ok == false) {
			System.out.println("\nInsira o mes: ");
			dataMes = sc.nextInt();
			ok = m.checkMes(dataMes);
		}
		ok = false;
		this.dataMes = dataMes;
		while (ok == false) {
			System.out.println("Insira o dia: ");
			dataDia = sc.nextInt();
			ok = d.checkDia(dataMes, dataDia);
		}
		this.dataDia = dataDia;
	}

	public void setQtdAlunos() {
		System.out.println("Quantidade maxima de alunos: ");
		this.qtdAlunos = sc.nextInt();
	}

	public String toString() {
		return "\nNome: "+nome+"\nLocal: " + local + "\nData: " + dataDia + "/" + dataMes
				+ "\nHorario: " + horarioInicio + "-" + horarioTermino
				+ "\nQuantidade maxima de Alunos: " + qtdAlunos;
	}
	public static boolean addAtividadeConstruida(String nome, Atividade dig) {
		if (AtividadesConstruidas.containsKey(Digital.nome))
			return false;
		AtividadesConstruidas.put(Digital.nome, (Digital) dig);
		return true;
	}
	public static boolean checkConflito(Digital dig1) {
		Iterator<Digital> it = AtividadesConstruidas.values().iterator();
		while (it.hasNext()) {
			Digital currentDigital = it.next();
			if (dig1.equals(currentDigital)) {
				
			} else if (dig1.getHorarioInicio() == currentDigital
					.getHorarioInicio()) {
				return false;
			}
		}
		return true;

	}
	
	public static boolean addAtividadeLista() {
		if (Atividade.listaAtividades.contains(nome))
			return false;
		Atividade.listaAtividades.add(nome);
		return true;
	}
	
	public static boolean makeListaAtividade(String nome) {
		boolean ok = false;
		AtividadeDigital digital = new AtividadeDigital();
		while (ok == false) {
			setName(nome);
			setName(nome.toLowerCase());
			ok = addAtividadeLista();
			if (ok == false) {
				return false;
			}
		}
		digital.setData();
		digital.setLocal();
		digital.setQtdAlunos();
		ok = false;
		while (ok == false) {
			digital.setHorario();
			addAtividadeConstruida(getName(), digital);
			ok = checkConflito(digital);
			if(ok == false){
				System.out.println("Conflito no horario, insira outro");
			}
		}
		return true;
	}
	
	public boolean addListaAtividadeLista(List<String> atividades) {
		Iterator<String> it = atividades.listIterator();
		Scanner sc = new Scanner(System.in);
		String atual = null;
		String atividade = null; 
		boolean ok = true;
		Atividade atividadeBuilder = null;
		while (it.hasNext()) {
			atual = it.next();
			atividade = null;
			while (ok == true) {
				System.out.println("Escolha o tipo de Atividade: ");
				System.out.println("Analogica, Digital ou Fisica");
				System.out.println("Digite a inicial: (A / D / F)");
				atividade = sc.nextLine();
				atividade = atividade.toLowerCase();
				ok = checkTipoAtividade(atividade);
			}
			if (atividade.equals("a")) {
				atividadeBuilder = new AtividadeAnalogica();
			} else if (atividade.equals("d")) {
				atividadeBuilder = new AtividadeDigital();
			} else if (atividade.equals("f")) {
				atividadeBuilder = new AtividadeFisica();
			}
			ok = makeListaAtividade(atual);
			if(ok == false) return false;
		}
		return false;
	}
	
	public static boolean checkTipoAtividade(String tipo) {
		if (tipo.equals("a") || tipo.equals("d") || tipo.equals("f")) {
			return true;
		}
		return false;
	}
	
	public void makeAtivGeral() {
		AtividadeDigital digital = new AtividadeDigital();
		digital.makeAtividade();
	}

	public String getLocal() {
		return this.local;
	}

	public int getHorarioInicio() {
		return this.horarioInicio;
	}

	public int getHorarioFim() {
		return this.horarioTermino;
	}

	public int getDataDia() {
		return this.dataDia;
	}

	public int getDataMes() {
		return this.dataMes;
	}

	public int getQtdAlunos() {
		return this.qtdAlunos;
	}


	public abstract void makeAtividade();

}
