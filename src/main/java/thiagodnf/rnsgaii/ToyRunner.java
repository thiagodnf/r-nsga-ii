package thiagodnf.rnsgaii;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.point.PointSolution;

import thiagodnf.rnsgaii.comparator.PreferenceDistanceComparator;
import thiagodnf.rnsgaii.gui.DataSet;
import thiagodnf.rnsgaii.gui.ScatterPlot;
import thiagodnf.rnsgaii.problem.ToyProblem;
import thiagodnf.rnsgaii.util.PointSolutionUtils;

public class ToyRunner extends AbstractRunner{
	
	public static void main(String[] args) {
		
		List<PointSolution> referencePoints = new ArrayList<>();

		referencePoints.add(PointSolutionUtils.createSolution(0.16, 0.9));
		
		List<DataSet> datasets = new ToyRunner().run(referencePoints, 0.000001);
		
		ScatterPlot.show(datasets, new double[] {0.0, 1.0}, new double[] {0.0, 1.0});
	}
	
	public List<DataSet> run(List<PointSolution> referencePoints, double epsilon) {
		
		System.out.println("Running " + ToyRunner.class.getSimpleName() + " w/ " + epsilon);
		
		List<DataSet> datasets = new ArrayList<>();
		
		DoubleProblem problem = new ToyProblem(30);

		datasets.add(new DataSet("Reference Points", referencePoints));
		
		int populationSize = 100;
		int maxEvaluations = 10000 * populationSize;
		
		CrossoverOperator<DoubleSolution> crossover = new SBXCrossover(0.9, 10.0);
	    MutationOperator<DoubleSolution> mutation = new PolynomialMutation(0.01, 20.0);
	    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = new BinaryTournamentSelection<DoubleSolution>(new PreferenceDistanceComparator<>()) ;
	    
	    List<DoubleSolution> populationForRNSGAII = runRNSGAII(problem, populationSize, maxEvaluations, crossover, mutation, selection, referencePoints, epsilon);
	    
	    datasets.add(new DataSet("R-NSGA-II w/ Epsilon=" + epsilon, PointSolutionUtils.convert(populationForRNSGAII)));
	    
	    System.out.println("Done");
	    
	    return datasets;
	}

}