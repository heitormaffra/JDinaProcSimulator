/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean;

import br.cesjf.model.entities.Atividade;
import br.cesjf.model.entities.Desenvolvedor;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import org.jfree.data.xy.XYSeries;
import org.primefaces.model.chart.CartesianChartModel;

/**
 *
 * @author heitormaffra
 */
@ManagedBean(name = "simulacaoBean")
@ViewScoped
public class SimulacaoBean {

    private static final Logger logger = Logger.getLogger(IndexBean.class.getName());
    private String nomeProjeto;
    private List<Atividade> atividades;
//    private List<MetaModel> desenvolvedores;
    private DataModel desenvModel;
    private List<Desenvolvedor> desenvolvedores;
    private Double artefatoDesenv;
    private List<IndexBean.ColumnModel> columns = new ArrayList<IndexBean.ColumnModel>();
    private CartesianChartModel linearModel;
    //private JynaSimulationData data;
    private DefaultSimulationData data;
    private List<String> tempValues;
    private Double expDes1;
    private Double expDes2;

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public DataModel getDesenvModel() {
        return desenvModel;
    }

    public void setDesenvModel(DataModel desenvModel) {
        this.desenvModel = desenvModel;
    }

    public List<Desenvolvedor> getDesenvolvedores() {
        return desenvolvedores;
    }

    public void setDesenvolvedores(List<Desenvolvedor> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }

    public Double getArtefatoDesenv() {
        return artefatoDesenv;
    }

    public void setArtefatoDesenv(Double artefatoDesenv) {
        this.artefatoDesenv = artefatoDesenv;
    }

    public List<IndexBean.ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<IndexBean.ColumnModel> columns) {
        this.columns = columns;
    }

    public CartesianChartModel getLinearModel() {
        return linearModel;
    }

    public void setLinearModel(CartesianChartModel linearModel) {
        this.linearModel = linearModel;
    }

    public DefaultSimulationData getData() {
        return data;
    }

    public void setData(DefaultSimulationData data) {
        this.data = data;
    }

    public List<String> getTempValues() {
        return tempValues;
    }

    public void setTempValues(List<String> tempValues) {
        this.tempValues = tempValues;
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

    public SimulacaoBean() {
    }

    public void realizaSimulacao() {
        try {
            JynaSimulationProfile profile = new DefaultSimulationProfile();
            JynaSimulationData data = new DefaultSimulationData();
            JynaSimulation simulation = new DefaultMetaModelInstanceSimulation();
            JynaSimulationMethod method = new DefaultMetaModelInstanceEulerMethod();

            // Modelo de Domínio
            br.ufjf.mmc.jynacore.metamodel.MetaModel domainModel = new DefaultMetaModel();
            domainModel.setName(nomeProjeto);

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

            for (Desenvolvedor desenv : desenvolvedores) {
                instanceModel.addNewClassInstance(desenv.getNmDsenv(), "Desenvolvedor");
                instanceModel.getClassInstances().get(desenv.getNmDsenv()).setProperty("experiência", Double.parseDouble(desenv.getExpDesenv().toString()));
            }

//            instanceModel.addNewClassInstance("D1", "Desenvolvedor");
//            instanceModel.addNewClassInstance("D2", "Desenvolvedor");        
//            instanceModel.getClassInstances().get("D1").setProperty(
//                    "experiência", expDes1);
//            instanceModel.getClassInstances().get("D2").setProperty(
//                    "experiência", expDes2);

            for (Atividade atv : atividades) {
                instanceModel.addNewClassInstance(atv.getNmAtivd(), "Atividade");
            }

//            instanceModel.addNewClassInstance("Projeto", "Atividade");
//            instanceModel.addNewClassInstance("Codificação", "Atividade");

            ClassInstance design = instanceModel.getClassInstances().get(
                    "Projeto");
            design.setProperty("duração", 20.0);
            design.setLink("Equipe", desenvolvedores.get(0).getNmDsenv());

            ClassInstance coding = instanceModel.getClassInstances().get(
                    "Codificação");
            coding.setProperty("duração", 15.0);
            coding.setLink("Precedente", "Projeto");
            coding.setLink("Equipe", desenvolvedores.get(1).getNmDsenv());

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


        } catch (Exception e) {
            System.err.println(e.getCause());
            e.printStackTrace();
        }
    }
}
