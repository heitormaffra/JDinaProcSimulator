/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.datamodel;

import br.cesjf.model.entities.Atividade;
<<<<<<< HEAD
=======
import java.io.Serializable;
>>>>>>> 107be7ddbb49c71473cc7677e2e8bef89261420f
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
<<<<<<< HEAD
 * @author heitor.filho
 */
public class AtividadeDataModel extends ListDataModel<Atividade> implements SelectableDataModel<Atividade>{

    public AtividadeDataModel(List<Atividade> data) {
        super(data);
=======
 * @author heitormaffra
 */
public class AtividadeDataModel extends ListDataModel<Atividade> implements SelectableDataModel<Atividade>, Serializable {

    public AtividadeDataModel(List<Atividade> atividades) {
        super(atividades);
>>>>>>> 107be7ddbb49c71473cc7677e2e8bef89261420f
    }
    
    

    @Override
<<<<<<< HEAD
    public Object getRowKey(Atividade t) {
        return t.getIdAtivd();
    }

    @Override
    public Atividade getRowData(String rowKey) {
        List<Atividade> atividades = (List<Atividade>) getWrappedData();
        for(Atividade atv : atividades){
            if(atv.getIdAtivd().equals(Integer.parseInt(rowKey))){
                return atv;
            }
        }
        return null;
    }
    
=======
    public Atividade getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
        
        int i = rowKey.indexOf("=");
        
        String id = rowKey.substring(i+1, rowKey.length() - 1);
        
        List<Atividade> atividades = (List<Atividade>) getWrappedData();
        
        for(Atividade atv : atividades) {
            if(atv.getIdAtivd().equals(Integer.valueOf(id)))
                return atv;
        }
        
        return null;
    }

    @Override
    public Object getRowKey(Atividade atv) {
        return atv;
    }
>>>>>>> 107be7ddbb49c71473cc7677e2e8bef89261420f
}
