/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean;

import br.cesjf.util.MetaModel;
import br.ufjf.mmc.jynacore.JynaSimulableModel;
import br.ufjf.mmc.jynacore.JynaSimulation;
import br.ufjf.mmc.jynacore.JynaSimulationData;
import br.ufjf.mmc.jynacore.JynaSimulationMethod;
import br.ufjf.mmc.jynacore.JynaSimulationProfile;
import br.ufjf.mmc.jynacore.expression.BooleanOperator;
import br.ufjf.mmc.jynacore.expression.Expression;
import br.ufjf.mmc.jynacore.expression.NumberOperator;
import br.ufjf.mmc.jynacore.expression.impl.DefaultExpression;
import br.ufjf.mmc.jynacore.expression.impl.DefaultNameExpression;
import br.ufjf.mmc.jynacore.expression.impl.DefaultNumberConstantExpression;
import br.ufjf.mmc.jynacore.impl.DefaultSimulationData;
import br.ufjf.mmc.jynacore.impl.DefaultSimulationProfile;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClass;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassAuxiliary;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassProperty;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassRate;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassStock;
import br.ufjf.mmc.jynacore.metamodel.MetaModelRelation;
import br.ufjf.mmc.jynacore.metamodel.MetaModelScenario;
import br.ufjf.mmc.jynacore.metamodel.MetaModelScenarioAffect;
import br.ufjf.mmc.jynacore.metamodel.MetaModelScenarioConnection;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModel;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClass;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassAuxiliary;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassProperty;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassRate;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassStock;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelMultiRelation;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelScenario;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelScenarioAffect;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelScenarioConnection;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelSingleRelation;
import br.ufjf.mmc.jynacore.metamodel.instance.ClassInstance;
import br.ufjf.mmc.jynacore.metamodel.instance.MetaModelInstance;
import br.ufjf.mmc.jynacore.metamodel.instance.impl.DefaultMetaModelInstance;
import br.ufjf.mmc.jynacore.metamodel.simulator.impl.DefaultMetaModelInstanceEulerMethod;
import br.ufjf.mmc.jynacore.metamodel.simulator.impl.DefaultMetaModelInstanceSimulation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.jfree.data.xy.XYSeries;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author heitor.filho
 */
@ViewScoped
@ManagedBean(name = "indexBean")
public class IndexBean {

    private static final Logger logger = Logger.getLogger(IndexBean.class.getName());
    private String nomeProjeto;
    private List<String> atividades;
    private List<MetaModel> desenvolvedores;
    private DataModel desenvModel;
    private String nomeDesenv;
    private Double artefatoDesenv;
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();
    private CartesianChartModel linearModel;
    //private JynaSimulationData data;
    private DefaultSimulationData data;
    private List<Values> dataValues;
    private List<String> tempValues;

    public List<String> getTempValues() {
        return tempValues;
    }

    public void setTempValues(List<String> tempValues) {
        this.tempValues = tempValues;
    }

    public List<Values> getValues() {
        return dataValues;
    }

    public void setValues(List<Values> values) {
        this.dataValues = values;
    }

    public DefaultSimulationData getData() {
        return data;
    }

    public void setData(DefaultSimulationData data) {
        this.data = data;
    }

    public IndexBean() {
        desenvolvedores = new ArrayList<MetaModel>();
        data = new DefaultSimulationData();
    }

    public CartesianChartModel getLinearModel() {
        return linearModel;
    }

    public void setLinearModel(CartesianChartModel linearModel) {
        this.linearModel = linearModel;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public String getNomeDesenv() {
        return nomeDesenv;
    }

    public void setNomeDesenv(String nomeDesenv) {
        this.nomeDesenv = nomeDesenv;
    }

    public Double getArtefatoDesenv() {
        return artefatoDesenv;
    }

    public void setArtefatoDesenv(Double artefatoDesenv) {
        this.artefatoDesenv = artefatoDesenv;
    }

    public DataModel getDesenvModel() {
        return desenvModel;
    }

    public void setDesenvModel(DataModel desenvModel) {
        this.desenvModel = desenvModel;
    }

    public List<MetaModel> getDesenvolvedores() {
        return desenvolvedores;
    }

    public void setDesenvolvedores(List<MetaModel> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }

    public List<String> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<String> atividades) {
        this.atividades = atividades;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public void save(ActionEvent actionEvent) {
        //Persist user  

        FacesMessage msg = new FacesMessage("Successful", "Welcome :");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String onFlowProcess(FlowEvent event) {
        logger.log(Level.INFO, "Current wizard step:{0}", event.getOldStep());
        logger.log(Level.INFO, "Next step:{0}", event.getNewStep());
        boolean skip = false;

        if (skip) {
            skip = false;   //reset in case user goes back  
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public final void createNewDesenvRow() {
        desenvModel = new ListDataModel(desenvolvedores);
    }
    DataModel model;

    public final void addNewDesenvRow() {
        MetaModel meta = new MetaModel(nomeDesenv, new Double(artefatoDesenv));
        desenvolvedores.add(meta);
        model = new ListDataModel(desenvolvedores);
    }

    public DataModel getDesenvolvedoresList() {
        return model;
    }

    public void realizaSimulacao() {
        try {
            JynaSimulationProfile profile = new DefaultSimulationProfile();
            JynaSimulationData data = new DefaultSimulationData();
            JynaSimulation simulation = new DefaultMetaModelInstanceSimulation();
            JynaSimulationMethod method = new DefaultMetaModelInstanceEulerMethod();

            // Modelo de Domínio
            br.ufjf.mmc.jynacore.metamodel.MetaModel domainModel = new DefaultMetaModel();
            domainModel.setName("Projeto de Software Simples");

            // Desenvolvedor Class
            MetaModelClass developer = new DefaultMetaModelClass();
            developer.setName("Desenvolvedor");

            MetaModelClassProperty experience = new DefaultMetaModelClassProperty();
            experience.setName("experiência");
            experience.setDefaultValue(1.0);
            developer.put(experience);

            MetaModelClassAuxiliary productivity = new DefaultMetaModelClassAuxiliary();
            productivity.setName("Produtividade");
            productivity
                    .setExpression(new DefaultNameExpression("experiência"));
            developer.put(productivity);

            domainModel.put(developer);

            // Atividade Class
            MetaModelClass activity = new DefaultMetaModelClass();
            activity.setName("Atividade");

            MetaModelClassProperty duration = new DefaultMetaModelClassProperty();
            duration.setName("duração");
            duration.setDefaultValue(25.0);
            activity.put(duration);

            MetaModelClassStock timeToConclude = new DefaultMetaModelClassStock();
            timeToConclude.setName("Tempo para Concluir");
            timeToConclude.setExpression(new DefaultNameExpression("duração"));
            activity.put(timeToConclude);

            MetaModelClassAuxiliary production = new DefaultMetaModelClassAuxiliary();
            production.setName("Produção");
            production.setExpression(new DefaultNumberConstantExpression(1.0));
            activity.put(production);

            MetaModelClassRate work = new DefaultMetaModelClassRate();
            work.setName("Trabalho");
            work.setSource(timeToConclude);
            Expression workExp = new DefaultExpression();
            workExp.setOperator(NumberOperator.MINIMUM);
            workExp.setLeftOperand(new DefaultNameExpression("Produção"));
            workExp.setRightOperand(new DefaultNameExpression(
                    "Tempo para Concluir"));
            work.setExpression(workExp);
            activity.put(work);

            domainModel.put(activity);

            MetaModelRelation team = new DefaultMetaModelSingleRelation();
            team.setName("Equipe");
            team.setSource(activity);
            team.setTarget(developer);
            domainModel.put(team);

            MetaModelRelation precedence = new DefaultMetaModelMultiRelation();
            precedence.setName("Precedente");
            precedence.setSource(activity);
            precedence.setTarget(activity);
            domainModel.put(precedence);

            // Modelo de Instância
            MetaModelInstance instanceModel = new DefaultMetaModelInstance();
            instanceModel.setMetaModel(domainModel);
            instanceModel.setName("Instância de Projeto com Cenários");

            instanceModel.addNewClassInstance("D1", "Desenvolvedor");
            instanceModel.addNewClassInstance("D2", "Desenvolvedor");

            instanceModel.getClassInstances().get("D1").setProperty(
                    "experiência", 1.0);
            instanceModel.getClassInstances().get("D2").setProperty(
                    "experiência", 0.5);

            instanceModel.addNewClassInstance("Projeto", "Atividade");
            instanceModel.addNewClassInstance("Codificação", "Atividade");

            ClassInstance design = instanceModel.getClassInstances().get(
                    "Projeto");
            design.setProperty("duração", 20.0);
            design.setLink("Equipe", "D1");

            ClassInstance coding = instanceModel.getClassInstances().get(
                    "Codificação");
            coding.setProperty("duração", 15.0);
            coding.setLink("Precedente", "Projeto");
            coding.setLink("Equipe", "D2");

            profile.setTimeLimits(50, 50.0);

            // Modelo de Cenário
            MetaModelScenario sceActTeam = new DefaultMetaModelScenario();
            sceActTeam.setName("Produção Baseada na Equipe");
            sceActTeam.setMetaModel(domainModel);

            MetaModelScenarioConnection connProdByTeam = new DefaultMetaModelScenarioConnection();
            connProdByTeam.setName("AAtividade");
            connProdByTeam.setClassName("Atividade");
            MetaModelScenarioAffect affEquipe = new DefaultMetaModelScenarioAffect();
            affEquipe.setName("Produção");
            affEquipe.setExpression(new DefaultNameExpression(
                    "Equipe.Produtividade"));
            connProdByTeam.getAffectList().add(affEquipe);

            sceActTeam.put(connProdByTeam);
            domainModel.putScenario(sceActTeam);
            coding.setScenarioConnection("Produção Baseada na Equipe",
                    "AAtividade");
            design.setScenarioConnection("Produção Baseada na Equipe",
                    "AAtividade");



            MetaModelScenario sceActPreced = new DefaultMetaModelScenario();
            sceActPreced.setName("Precedência de Atividades");
            sceActPreced.setMetaModel(domainModel);

            MetaModelScenarioConnection connTaskPred = new DefaultMetaModelScenarioConnection();
            connTaskPred.setName("AAtividade");
            connTaskPred.setClassName("Atividade");
            MetaModelScenarioAffect affProduction = new DefaultMetaModelScenarioAffect();
            affProduction.setName("Trabalho");
            Expression expIf = new DefaultExpression(NumberOperator.IF);
            Expression expGt = new DefaultExpression(BooleanOperator.LOWERTHAN);
            Expression expGroup = new DefaultExpression(NumberOperator.GROUPSUM);
            expGroup.setLeftOperand(new DefaultNameExpression("Precedente"));
            expGroup.setRightOperand(new DefaultNameExpression("Tempo para Concluir"));
            expGt.setLeftOperand(expGroup);
            expGt.setRightOperand(new DefaultNumberConstantExpression(0.041));
            expIf.setLeftOperand(expGt);
            expIf.setMiddleOperand(new DefaultNameExpression("Trabalho"));
            expIf.setRightOperand(new DefaultNumberConstantExpression(0.0));
            affProduction.setExpression(expIf);
            connTaskPred.getAffectList().add(affProduction);

            sceActPreced.put(connTaskPred);
            domainModel.putScenario(sceActPreced);
            coding.setScenarioConnection("Precedência de Atividades",
                    "AAtividade");
            design.setScenarioConnection("Precedência de Atividades",
                    "AAtividade");



            simulation.setProfile(profile);
            simulation.setMethod(method);
            data
                    .addAll(((JynaSimulableModel) instanceModel)
                    .getAllJynaValued());
            simulation.setSimulationData(data);
            simulation.setModel((JynaSimulableModel) instanceModel);
            simulation.reset();
            data.register(0.0);
            simulation.run();
            // data.register(36.0);
            XYSeries xyserie = new XYSeries(data.getWatchedNames().get(0));
            System.out.println(data.getWatchedNames().get(0));
            //System.out.println(data);
            
            this.data = (DefaultSimulationData) data;

            columns.clear();
            dataValues = new ArrayList<Values>();
            
            tempValues = new ArrayList<String>();

            for (int i = 0; i < data.getWatchedCount(); i++) {
                dataValues.add(new Values());
                dataValues.get(i).setIdentficador(data.getWatchedNames().get(i));
                ColumnModel column = new ColumnModel();
                column.setHeader(data.getWatchedNames().get(i).replace(".", " - "));
                for (int j = 1; j < data.getWatchedSize(); j++) {
                    column.getProperty().add(data.getValue(i, j).toString());
                    tempValues.add(data.getValue(i, j).toString());
                    
                }
                dataValues.get(i).setValue(tempValues);
                columns.add(column);
            }
            generateChart(data);
        } catch (Exception e) {
            System.err.println(e.getCause());
            e.printStackTrace();
        }
    }

    public void generateChart(JynaSimulationData data) {

        linearModel = new CartesianChartModel();
        List<LineChartSeries> lines = new ArrayList<LineChartSeries>();

        for (int i = 0; i < data.getWatchedCount(); i++) {
            lines.add(new LineChartSeries());
            lines.get(i).setLabel(data.getWatchedNames().get(i));
            for (int j = 0; j < data.getWatchedSize(); j++) {
                lines.get(i).set(data.getTime(j), data.getValue(i, j));
            }
        }

//        LineChartSeries trabalho = new LineChartSeries();
//        trabalho.setLabel("Trabalho");
//
//        for (int i = 0; i < data.getWatchedSize(); i++) {
//            if (i % 2 == 0) {
//                trabalho.set(data.getTime(i), data.getValue(1, i));
//            }
//        }
//        linearModel.addSeries(trabalho);

        for (LineChartSeries line : lines) {
            linearModel.addSeries(line);
        }

    }

    static public class ColumnModel implements Serializable {

        private String header;
        private List<String> property;

        public ColumnModel(String header, List<String> property) {
            this.header = header;
            this.property = property;
        }

        private ColumnModel() {
            property = new ArrayList<String>();
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public void setProperty(List<String> property) {
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public List<String> getProperty() {

            return property;
        }
    }
}
