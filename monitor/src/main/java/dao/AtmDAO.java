package dao;

import model.Computador;

import java.sql.CallableStatement;
import java.util.List;
import controller.*;
import model.MAC;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oshi.software.os.OSFileStore;
import oshi.software.os.OSProcess;

public class AtmDAO {

	private PreparedStatement ps = null;

	public void atualizarHd() throws SQLException {
		HDController hc = new HDController();
		Connection cnx = new Banco().getInstance();
		int idAtm = new SelectMonetizando().getIdComputador();
		for (OSFileStore p : hc.getInformacoesHd()) {
			
			
			String SQL = "INSERT INTO TBD_HD(TOTAL,USADO,DIRETORIO,UUID,TIPO_DIR,VOLUME,ID_ATM, PORCETAGEM_USO) "
					+ "VALUES (?,?,?,?,?,?,?,?)";

			try {
				PreparedStatement ps = cnx.prepareStatement(SQL);
				ps.setLong(1, p.getTotalSpace());
				ps.setLong(2, p.getUsableSpace());
				ps.setString(3, p.getMount());
				ps.setString(4, p.getUUID());
				ps.setString(5, p.getType());
				ps.setString(6, p.getVolume());
				ps.setInt(7, idAtm);
				ps.setLong(8, this.calculoPorcEspacoLivre(p.getTotalSpace(), p.getUsableSpace()));
				ps.executeUpdate();
				System.out.println("hd atualizado......");
			} catch (SQLException erro) {
				System.out.println("Algum erro aconteceu...");
				erro.printStackTrace();
			}

		}
	}

	private long calculoPorcEspacoLivre(long total, long usando) {
		try {
			return (usando * 100) / total;
		} catch(Exception e) {
			System.out.println(e);
			return 0;
		}
		

	}

	public String cadastroInicial(int idAgencia, int idBanco) {

		List<MAC> macs = new RedeController().getMacsPC();

		if (!isPcJaCadastrado(macs)) {
			int idAtm = cadastroComputadorInicio(idAgencia, idBanco);
			boolean cadastrouMac = cadastrarMACS(macs, idAtm);

			return "\n---------------------------------------------------------\n" + "ID_COMPUTADOR: " + idAtm + ""
					+ "\nMAC CADASTRADOS!" + "\n---------------------------------------------------------";
		} else {

			return "Computador já cadastrado...";

		}

	}

	/**
	 * METODO PARA DESCOBRIR SE O MAC DO COMPUTADOR JA ESTA CADASTRADO NO BANCO,
	 * CASO J� ESTEJA O RETORNO EH TRUE
	 *
	 * List <MAC> vem da classe Rede, presenta no package controller. no metodo
	 * getMacsPC()
	 *
	 * @return TRUE = JA TEM PC CADASTRADO COM ESSE MAC | FALSE = NAO TEM
	 */
	public boolean jaCadastrado() {
		return isPcJaCadastrado(new RedeController().getMacsPC());
	}

	public boolean isPcJaCadastrado(List<MAC> mac) {
		String SQL = "SELECT * FROM TBD_ATM_MAC WHERE MAC_ADDRESS = ?";

		for (MAC m : mac) {
			Connection cnx = new Banco().getInstance();

			try {

				cnx.setAutoCommit(true);
				PreparedStatement ps = cnx.prepareStatement(SQL);
				ps.setString(1, m.getMac());

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					// System.out.println(rs);
					return true;

				}

			} catch (SQLException sqlEx) {
				System.out.print("ERRO SQL0003: ");
				try {
					if (!cnx.isClosed()) {
						cnx.rollback();
					}

					sqlEx.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.print("ERRO DESC0001: ");
				e.getMessage();
			} finally {
				try {

					if (!cnx.isClosed()) {

						cnx.close();

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return false;// se chegar ate aqui quer dizer que n�o tem o MAC cadastrado
	}
	
	
	public int getIdAtm(List<MAC> mac) {
		String SQL = "SELECT * FROM TBD_ATM_MAC WHERE MAC_ADDRESS = ?";

		for (MAC m : mac) {
			Connection cnx = new Banco().getInstance();

			try {

				cnx.setAutoCommit(true);
				PreparedStatement ps = cnx.prepareStatement(SQL);
				ps.setString(1, m.getMac());

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					// System.out.println(rs);
					System.out.println(rs.getInt("ID_ATM"));
					return rs.getInt("ID_ATM");

				}

			} catch (SQLException sqlEx) {
				System.out.print("ERRO SQL0003: ");
				try {
					if (!cnx.isClosed()) {
						cnx.rollback();
					}

					sqlEx.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.print("ERRO DESC0001: ");
				e.getMessage();
			} finally {
				try {

					if (!cnx.isClosed()) {

						cnx.close();

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return 0;
	}
	
	public int getIdAgencia(int idAtm) {
		String SQL = "SELECT * FROM TBD_ATM WHERE ID_ATM = ?";

		
			Connection cnx = new Banco().getInstance();

			try {

				cnx.setAutoCommit(true);
				PreparedStatement ps = cnx.prepareStatement(SQL);
				ps.setInt(1, idAtm);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					// System.out.println(rs);
					System.out.println(rs.getInt("ID_AGENCIA"));
					return rs.getInt("ID_AGENCIA");

				}

			} catch (SQLException sqlEx) {
				System.out.print("ERRO SQL0003: ");
				try {
					if (!cnx.isClosed()) {
						cnx.rollback();
					}

					sqlEx.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.print("ERRO DESC0001: ");
				e.getMessage();
			} finally {
				try {

					if (!cnx.isClosed()) {

						cnx.close();

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		

		return 0;
	}
	
	
	

	/**
	 * METODO QUE FAZ O PRIMEIRO CADASTRO DE UM COMPUTADOR NA TABELA PEEK_COMPUTADOR
	 *
	 * @return RETORNA O ID_COMPUTADOR DO COMPUTADOR CADASTRADO
	 */
	public int cadastroComputadorInicio(int idAgencia, int idBanco) {

		String SQL = "INSERT INTO TBD_ATM (QUANTIDADE_MEMORIA_RAM, DESCRICAO_PROCESSADOR,"
				+ "MAC_ADDRESS_INICIAL, ID_AGENCIA, ID_USUARIO) VALUES (?,?,?,?,?)";
		Connection cnx = new Banco().getInstance();
		int idComputador = -1;
		try {

			Computador computador = new Computador();

			cnx.setAutoCommit(false);
			PreparedStatement ps = cnx.prepareStatement(SQL);

			ps.setDouble(1, computador.getRam().getTotal());
			ps.setString(2, computador.getProcessador().getNomeProcessador());
			ps.setString(3, computador.getRede().getMacParaCadastroInicial());
			ps.setInt(4, idAgencia);
			ps.setInt(5, idBanco);

			if (ps.executeUpdate() > 0) {
				cnx.commit();
				System.out.println("COMPUTADOR CRIADO");
				idComputador = new SelectMonetizando().getIdComputador();
				return idComputador;
			}

		} catch (SQLException sqlEx) {
			System.out.print("ERRO SQL0003: ");
			try {
				if (!cnx.isClosed()) {
					cnx.rollback();
				}

				sqlEx.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.print("ERRO DESC0001: ");
			e.getMessage();
		} finally {
			try {

				if (!cnx.isClosed()) {

					cnx.close();

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return idComputador;

	}

	/**
	 * PEGA O ULTIMO ID_ATM CADASTRADO, ESSE METODO SERVE DE COMPLEMENTO PARA O
	 * METODO cadastroComputadorInicio()
	 *
	 * @param computador ATM QUE ACABOU DE SER CADASTRADO
	 * @return ULTIMO ID_ATM CADASTRADO
	 */
	/**
	 * METODO PARA CADASTRAR TODOS OS MAC ADDRESS DO COMPUTADOR
	 *
	 * @param mac          List<MAC> vem da classe Rede, presenta no package
	 *                     controller. no metodo getMacsPC()
	 * @param idComputador vem do metodo cadastroComputadorInicio() desta mesma
	 *                     classe
	 * @return retorna TRUE se todos mac cadastrarem no banco
	 */
	public boolean cadastrarMACS(List<MAC> mac, int idComputador) {

		String SQL = "INSERT INTO TBD_ATM_MAC(MAC_ADDRESS,TIPO_CONEXAO,ID_ATM) VALUES (?,?,?)";
		int cadastrados = 0;

		for (MAC m : mac) {

			Connection cnx = new Banco().getInstance();
			try {

				cnx.setAutoCommit(false);
				PreparedStatement ps = cnx.prepareStatement(SQL);

				ps.setString(1, m.getMac());
				ps.setString(2, m.getAdaptador());
				ps.setInt(3, idComputador);

				if (ps.executeUpdate() > 0) {
					cnx.commit();
					System.out.println(
							"MAC: " + m.getMac() + "\nADAPTADOR: " + m.getAdaptador() + "\nCADASTRADO COM SUCESSO!");
					cadastrados++;
				}

			} catch (SQLException sqlEx) {
				System.out.print("ERRO SQL0003: ");
				try {
					if (!cnx.isClosed()) {
						cnx.rollback();
					}

					sqlEx.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.print("ERRO DESC0001: ");
				e.getMessage();
			} finally {
				try {

					if (!cnx.isClosed()) {

						cnx.close();

					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return cadastrados == mac.size();

	}

	/*
	 * ATUALIZAÇÃO DE INFORMAÇÕES DO COMPUTADOR E DA REDE AUTOMÁTICAS
	 *
	 * Descrição: O método abaixo é executado repetidas vezes com um intervalo de 5
	 * minutos a cada execução, ele pega as informações do computador, insere na na
	 * procedure e executada a procedura em linguagem SQL;
	 * 
	 * As informações necessárias são inseridas na String da procedure e a mesma é
	 * "enviada" para a execução depois da conexão com o banco for iniciada. A query
	 * passa pelo tratamento para verificar se foi realizada e atualizada as
	 * informações ou se ocorreu algum erro.
	 */
	public void atualizacaoAutomatica() throws SQLException, InterruptedException {

		Computador c = new Computador();

		Connection cnx = new Banco().getInstance();
		CallableStatement ps;
		ps = cnx.prepareCall("{CALL Sp_adicionar_informacoes(?,?,?,?,?,?,?)}");

		try {
			cnx.setAutoCommit(true);
			ps.setString("@TEMPO_ATIVIDADE", c.getProcessador().getTempoAtividade() + " ");
			ps.setString("@PORCENTAGEM_USO", c.getProcessador().getPorcetagemDeUso() + " ");
			ps.setString("@MAC_ADDRESS", c.getRede().getMAC());
			ps.setDouble("@TOTAL", c.getRam().getTotal());
			ps.setDouble("@LIVRE", c.getRam().getDisponivel());
			ps.setDouble("@EM_USO", c.getRam().getUsando());
			ps.setInt("@PORCENTAGEM_USO_RAM", c.getRam().getPorcentagemUso());
			ps.execute();

			String processos_ = "";
			for (OSProcess o : c.getProcesso().getProcessos()) {
				processos_ += o.getName() + ";";
			}

			new log_Monet.ArquivoLogger(
					"TEMPO_ATIVIDADE: " + c.getProcessador().getTempoAtividade() + System.getProperty("line.separator")
							+ "HD" + c.getHD().getArvore() + System.getProperty("line.separator") + "PROCESSO"
							+ processos_ + System.getProperty("line.separator") + "MERORIA RAM"
							+ c.getRam().getDisponivel() + System.getProperty("line.separator") + ""
							+ c.getRam().getTotal() + System.getProperty("line.separator") + "");

			System.out.println("atualizado..");
			if (c.getRam().getPorcentagemUso() > 70) {
				new NotificacaoController().usoMemoriaRAM();
			}
			if (c.getProcessador().getPorcetagemDeUso() > 70) {
				new NotificacaoController().usoProcessador();
			}

		} catch (SQLException sqlEx) {
			System.out.println("Algum erro aconteceu...");
			new log_Monet.ArquivoLogger(sqlEx.getSQLState());
			sqlEx.printStackTrace();
		}
	}
}
