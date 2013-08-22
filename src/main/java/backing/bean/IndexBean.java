/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backing.bean;

import br.ufjf.mmc.jynacore.JynaSimulableModel;
import br.ufjf.mmc.jynacore.JynaSimulation;
import br.ufjf.mmc.jynacore.JynaSimulationData;
import br.ufjf.mmc.jynacore.JynaSimulationMethod;
import br.ufjf.mmc.jynacore.JynaSimulationProfile;
import br.ufjf.mmc.jynacore.JynaValued;
import br.ufjf.mmc.jynacore.expression.Expression;
import br.ufjf.mmc.jynacore.expression.impl.DefaultExpression;
import br.ufjf.mmc.jynacore.expression.impl.DefaultNameExpression;
import br.ufjf.mmc.jynacore.expression.impl.DefaultNumberConstantExpression;
import br.ufjf.mmc.jynacore.impl.DefaultSimulationData;
import br.ufjf.mmc.jynacore.impl.DefaultSimulationProfile;
import br.ufjf.mmc.jynacore.metamodel.MetaModel;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClass;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassAuxiliary;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassProperty;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassRate;
import br.ufjf.mmc.jynacore.metamodel.MetaModelClassStock;
import br.ufjf.mmc.jynacore.metamodel.MetaModelRelation;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModel;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClass;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassAuxiliary;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassProperty;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassRate;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelClassStock;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelMultiRelation;
import br.ufjf.mmc.jynacore.metamodel.impl.DefaultMetaModelSingleRelation;
import br.ufjf.mmc.jynacore.metamodel.instance.ClassInstanceItem;
import br.ufjf.mmc.jynacore.metamodel.instance.MetaModelInstance;
import br.ufjf.mmc.jynacore.metamodel.instance.impl.DefaultMetaModelInstance;
import br.ufjf.mmc.jynacore.metamodel.simulator.impl.DefaultMetaModelInstanceEulerMethod;
import br.ufjf.mmc.jynacore.metamodel.simulator.impl.DefaultMetaModelInstanceSimulation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author heitor.filho
 */
@ViewScoped
@ManagedBean(name = "indexBean")
public class IndexBean {

    private CartesianChartModel model;

    /**
     *
     * @throws Exception
     */
    public IndexBean() throws Exception {



        //Modelo de Domínio
        MetaModel domainModel = new DefaultMetaModel();
        domainModel.setName("Projeto de Software Simples");

        //Classe Desenvolvedor
        MetaModelClass developer = new DefaultMetaModelClass();
        developer.setName("Heitor");

        MetaModelClassProperty experience = new DefaultMetaModelClassProperty();
        experience.setName("experiência");
        experience.setDefaultValue(2.0);
        developer.put(experience);

        MetaModelClassAuxiliary productivity = new DefaultMetaModelClassAuxiliary();
        productivity.setName("Produtividade");
        productivity.setExpression(new DefaultNameExpression("experiência"));
        developer.put(productivity);

        domainModel.put(developer);

        //Classe Atividade
        MetaModelClass activity = new DefaultMetaModelClass();
        activity.setName("Atividade");

        MetaModelClassProperty duration = new DefaultMetaModelClassProperty();
        duration.setName("duração");
        duration.setDefaultValue(25.0);
        activity.put(duration);

        MetaModelClassStock timeToConclude = new DefaultMetaModelClassStock();
        timeToConclude.setName("Tempo para concluir");
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
        workExp.setLeftOperand(new DefaultNameExpression("Produção"));
        workExp.setRightOperand(new DefaultNameExpression("Tempo para concluir"));
        work.setExpression(workExp);
        activity.put(work);

        domainModel.put(activity);

        MetaModelRelation team = new DefaultMetaModelSingleRelation();
        team.setName("Equipe");
        team.setTarget(developer);
        domainModel.put(team);

        MetaModelRelation precedence = new DefaultMetaModelMultiRelation();
        precedence.setName("Precedente");
        precedence.setSource(activity);
        precedence.setTarget(activity);
        domainModel.put(precedence);

        MetaModelInstance modelInstance = new DefaultMetaModelInstance();
        modelInstance.setMetaModel(domainModel);


        JynaSimulation simulation = new DefaultMetaModelInstanceSimulation();
        JynaSimulationProfile profile = new DefaultSimulationProfile();
        JynaSimulationMethod method = new DefaultMetaModelInstanceEulerMethod();
        JynaSimulableModel instance;
        DefaultSimulationData data = new DefaultSimulationData();

        instance = modelInstance;
        profile.setInitialTime(0.0);
        profile.setFinalTime(500.0);
        profile.setTimeLimits(1000, 10.0);
        int skip = 10;

        simulation.setMethod(method);
        simulation.setProfile(profile);
        data.removeAll();
        data.clearAll();

        simulation.setModel(instance);
        simulation.setSimulationData(
                (JynaSimulationData) data);
        simulation.reset();

        data.register(1.0);
        runSimulation(simulation, skip);

//        model = new CartesianChartModel();
//        ChartSeries boys = new ChartSeries();
//        boys.setLabel("Boys");
//        boys.set("2004", 120);
//        boys.set("2005", 100);
//        ChartSeries girls = new ChartSeries();
//        girls.setLabel("Girls");
//        girls.set("2004", 52);
//        girls.set("2005", 60);
//        model.addSeries(boys);
//        model.addSeries(girls);
        for (JynaValued jv : instance.getAllJynaValued()) {
            System.out.println(jv.getValue());
            ClassInstanceItem cii = (ClassInstanceItem) jv;
            if (cii.getName().equals("")) {
                data.add(cii.getClassInstance().getName() + "." + cii.getName(), jv);
                System.out.println(cii.getClassInstance().getName() + "." + cii.getName() + "\n");
            }
            //System.out.println(cii.getClassInstance().getName() + "." + cii.getName() + "| Valor: "+ jv.getValue());
            System.out.println("Nome: " + jv.getName() + "| Valor: " + jv.getValue());

            model = new CartesianChartModel();
            ChartSeries valor = new ChartSeries();
            valor.setLabel("Atividade");
            valor.set(cii.getName(), jv.getValue());
            model.addSeries(valor);

        }
    }

    private static void runSimulation(JynaSimulation simulation, int skip) throws Exception {
        //simulation.run();
        int steps = simulation.getProfile().getTimeSteps();


        System.out.println("Simulating with " + simulation.getProfile().getTimeSteps() + " iterations. Interval " + simulation.getProfile().getTimeInterval() + " to " + simulation.getProfile().getFinalTime());
        for (int i = 0; i < steps; i++) {
            simulation.step();
            if (i % skip == 0) {
                simulation.register();
            }
        }
        //System.out.println("Simulating done!");
    }

    /**
     *
     * @return
     */
    public CartesianChartModel getModel() {
        return model;
    }
}
