/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean;

import br.cesjf.model.entities.Atividade;
import br.cesjf.model.entities.Desenvolvedor;
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
import br.ufjf.mmc.jynacore.metamodel.exceptions.instance.MetaModelInstanceInvalidPropertyException;
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
import java.util.UUID;
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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

/**
 *
 * @author heitor.filho
 */
public class IndexBean {

    public IndexBean() {
        desenvolvedores = new ArrayList<MetaModel>();
        data = new DefaultSimulationData();


        atvsToAdd = new ArrayList<Atividade>();
        atvsToAdd.add(new Atividade(1, "Codificar", 10d, new Desenvolvedor(1, "Heitor")));
        atvsToAdd.add(new Atividade(2, "QA", 10d, new Desenvolvedor(2, "Ramon")));
        atvsToAdd.add(new Atividade(3, "Arquitetar", 10d, new Desenvolvedor(3, "Lucas")));
        atvsToAdd.add(new Atividade(4, "Projetar", 10d, new Desenvolvedor(4, "João")));
        atvsToAdd.add(new Atividade(5, "Homologar", 10d, new Desenvolvedor(5, "Diogo")));


        ativdsSelecteds = new ArrayList<Atividade>();

//        ativSelecionada = new Atividade();


    }
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
    private List<String> tempValues;
    private Double expDes1;
    private Double expDes2;
    private List<Atividade> ativdsSelecteds;
    private List<Atividade> atvsToAdd;
    private DataModel rowsData;
    private String nomeAtividade;
    private Double duracaoAtividade;
    private Desenvolvedor desenvRespAtv;
    private Atividade ativSelecionada;
    private MindmapNode root;
    private MindmapNode selectedNode;
    private MetaModelInstance instanceModel;
    DefaultMetaModel domainModel;

    public MetaModelInstance getInstanceModel() {
        return instanceModel;
    }

    public void setInstanceModel(MetaModelInstance instanceModel) {
        this.instanceModel = instanceModel;
    }

    public MindmapNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(MindmapNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public MindmapNode getRoot() {
        return root;
    }

    public void setRoot(MindmapNode root) {
        this.root = root;
    }

    public Atividade getAtivSelecionada() {
        return ativSelecionada;
    }

    public void setAtivSelecionada(Atividade ativSelecionada) {
        this.ativSelecionada = ativSelecionada;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public Double getDuracaoAtividade() {
        return duracaoAtividade;
    }

    public void setDuracaoAtividade(Double duracaoAtividade) {
        this.duracaoAtividade = duracaoAtividade;
    }

    public Desenvolvedor getDesenvRespAtv() {
        return desenvRespAtv;
    }

    public void setDesenvRespAtv(Desenvolvedor desenvRespAtv) {
        this.desenvRespAtv = desenvRespAtv;
    }

    public DataModel getRowsData() {
        return rowsData;
    }

    public void setRowsData(DataModel rowsData) {
        this.rowsData = rowsData;
    }

    public List<Atividade> getAtvsToAdd() {
        return atvsToAdd;
    }

    public void setAtvsToAdd(List<Atividade> atvsToAdd) {
        this.atvsToAdd = atvsToAdd;
    }

    public List<Atividade> getAtivdsSelecteds() {
        return ativdsSelecteds;
    }

    public void setAtivdsSelecteds(List<Atividade> ativdsSelecteds) {
        this.ativdsSelecteds = ativdsSelecteds;
    }

    public void verifica() {
        getAtivdsSelecteds().add(ativSelecionada);
    }

    public Double getExpDes1() {
        return expDes1;
    }

    public void setExpDes1(Double expDes1) {
        this.expDes1 = expDes1;
    }

    public Double getExpDes2() {
        return expDes2;
    }

    public void setExpDes2(Double expDes2) {
        this.expDes2 = expDes2;
    }

    public List<String> getTempValues() {
        return tempValues;
    }

    public void setTempValues(List<String> tempValues) {
        this.tempValues = tempValues;
    }

    public DefaultSimulationData getData() {
        return data;
    }

    public void setData(DefaultSimulationData data) {
        this.data = data;
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
            domainModel = new DefaultMetaModel();

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
            instanceModel = new DefaultMetaModelInstance();
            instanceModel.setMetaModel(domainModel);
            instanceModel.setName("Instância de Projeto com Cenários");

            instanceModel.addNewClassInstance("D1", "Desenvolvedor");
            instanceModel.addNewClassInstance("D2", "Desenvolvedor");

            instanceModel.getClassInstances().get("D1").setProperty(
                    "experiência", expDes1);
            instanceModel.getClassInstances().get("D2").setProperty(
                    "experiência", expDes2);

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

            tempValues = new ArrayList<String>();

            for (int i = 0; i < data.getWatchedCount(); i++) {
                ColumnModel column = new ColumnModel();
                column.setHeader(data.getWatchedNames().get(i).replace(".", " - "));
                for (int j = 1; j < data.getWatchedSize(); j++) {
                    column.getProperty().add(data.getValue(i, j).toString());
                    tempValues.add(data.getValue(i, j).toString());

                }
                columns.add(column);
            }
            generateChart(data);

            root = new DefaultMindmapNode(instanceModel.getName(), domainModel.getName(), "FFCC00", false);

            MindmapNode desenvol = new DefaultMindmapNode("Desenvolvedores", "Desenvolvedores", "6e9ebf", true);
            MindmapNode atvds = new DefaultMindmapNode("Atividades", "Atividades", "6e9ebf", true);
            MindmapNode equipe = new DefaultMindmapNode("Equipe", "Equipe", "6e9ebf", true);

            root.addNode(desenvol);
            root.addNode(atvds);
            root.addNode(equipe);

//            for (String c : instanceModel.getMetaModel().getScenarios().keySet()) {
//                root.addNode(new DefaultMindmapNode(domainModel.getScenarios().get(c).getName(), domainModel.getScenarios().get(c).getName(), "82c542"));
//            }
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
//        linearModel.addSeries(lines.get(1));
//        linearModel.addSeries(lines.get(5));
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(IndexBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();

        //populate if not already loaded  
        if (node.getChildren().isEmpty()) {
            Object label = node.getLabel();

            if (label.equals("Desenvolvedores")) {
                for (String d : domainModel.keySet()) {
                    for (String c : instanceModel.getClassInstances().keySet()) {
                        if (d.equals("Desenvolvedor")) {
                            node.addNode(new DefaultMindmapNode(instanceModel.getClassInstances().get(c).getName(), instanceModel.getClassInstances().get(c).getName(), "82c542"));
                        } else {
                            break;
                        }
                    }
                }
            } else if (label.equals("IPs")) {
                for (int i = 0; i < 18; i++) {
                    node.addNode(new DefaultMindmapNode("1.1.1." + i, "IP Number: 1.1.1." + i, "fce24f"));
                }

            } else if (label.equals("Malware")) {
                for (int i = 0; i < 18; i++) {
                    String random = UUID.randomUUID().toString();
                    node.addNode(new DefaultMindmapNode("Malware-" + random, "Malicious Software: " + random, "3399ff", false));
                }
            }
        }

    }

    public void onNodeDblselect(SelectEvent event) {
        this.selectedNode = (MindmapNode) event.getObject();
    }
}
