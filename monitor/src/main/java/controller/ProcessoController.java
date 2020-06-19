
package controller;

import dao.ProcessoDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Processo;
import oshi.PlatformEnum;
import static oshi.PlatformEnum.WINDOWS;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

/**
 *
 * @author brino
 */
public class ProcessoController {

    private SystemInfo si = null;
    private ProcessoDAO processo = null;

    public List<OSProcess> getProcessos() {
        si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        OSProcess[] processes = os.getProcesses(5000, null);
        List<Integer> pids = new ArrayList<Integer>();
        for (OSProcess p : processes) {
            if (!this.processoDoSistema(p)) {
                pids.add(p.getProcessID());
            }
        }
        // query for just those processes
        Collection<OSProcess> processes1 = os.getProcesses(pids);
        // theres a potential for a race condition here, if a process we queried
        // for initially wasn't running during the second query. In this case,
        // try again with the shorter list

        List<OSProcess> proces = os.getProcesses(pids);
        

        return proces;

    }

    private boolean processoDoSistema(OSProcess processo) {

        //if(SystemInfo.getCurrentPlatformEnum().WINDOWS.equals("WINDOWS")){
        if (processo.getName().equals("svchost")) {
            return true;
        }

        if (processo.getUser().trim().equals("")) {
            return true;
        }

        if (processo.getUser().equals("SISTEMA")) {
            return true;
        }

        //if (processo.getPath().contains("C:\\Windows\\System32"))
        //    return true;
        return false;

        // }
        // return false;
    }

    public void finalizarProcesso(int proc) {
        try {
            Runtime.getRuntime().exec("taskkill /PID " + proc + " /F");
        } catch (IOException ex) {
            Logger.getLogger(ProcessoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * PRECISA TER A EXTENSÃO
     *
     * @param proc
     */
    public void finalizarProcesso(String proc) {
        try {
            Runtime.getRuntime().exec("taskkill /T /F /IM" + proc);
            System.out.println(Runtime.getRuntime());
        } catch (IOException ex) {
            Logger.getLogger(ProcessoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Processo> selectProcessosParaFinalizar() {
        processo = new ProcessoDAO();
        return processo.selectProcessosParaFinalizar();
    }

    public void deleteProcessosParaFinalizar() {
        processo = new ProcessoDAO();
        List<Processo> procs = processo.selectProcessosParaFinalizar();
        int totalDelete = 0;
        for (Processo processoParaFinalizar : procs) {
            ProcessoDAO pd = new ProcessoDAO();

            this.finalizarProcesso(processoParaFinalizar.getPid());
            totalDelete += pd.deleteProcessosParaFinalizar(processoParaFinalizar);
        }

        if (totalDelete == procs.size()) {
            System.out.println("todos processos solicitados foram fechados");
        } else {
            System.out.println("totalDeletado = " + totalDelete + " totalListaProcessos = " + procs.size());
        }

    }

    public int insertProcesso() {
        processo = new ProcessoDAO();
        return processo.insertProcesso();
    }

    public void executarComandoRemoto(String comando) {
        try {
            Runtime.getRuntime().exec(comando);
        } catch (IOException ex) {
            Logger.getLogger(ProcessoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getProcessoQueUsamInternet() throws IOException {

        if (SystemInfo.getCurrentPlatformEnum() == WINDOWS) {

            Scanner in = new Scanner(Runtime.getRuntime().exec("netstat /b").getInputStream());
            int cont = 0;

            StringBuilder sb = new StringBuilder();

            while (in.hasNextLine()) {

                if (cont > 2) {
                    sb.append(in.nextLine() + "\n");
                } else {
                    in.nextLine();
                }

                cont++;
            }

            return sb.toString();

        }
        return "SISTEMA INVALIDO";
    }

    public int isProcessoConsumeInternet(String processo, String netstatB) {
        if (netstatB.contains(processo)) {
            return 1;
        } else if (netstatB.contains("SISTEMA INVALIDO")) {
            return -1;
        }else{
            return 0;
        }

    }

}
