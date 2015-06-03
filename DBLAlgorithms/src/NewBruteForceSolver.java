
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class NewBruteForceSolver {
    
    Stack<Label> disabledLabels = new Stack();
    List<Label> bestSolution = new ArrayList();
    
    public void bruteForce(List<Label> possibleLabels, List<Label> currentSolution) {
        if (possibleLabels.isEmpty() || bestSolution.size() == Globals.numberOfPoints) {
            return;
        }
        
        for (Label label : possibleLabels) {
            // TODO: find the overlaps with this label, query quadtree
            List<Label> overlaps = new ArrayList();
            possibleLabels.remove(label);
            int removedLAmount = 0;
            for (Label overlap : overlaps) {
                possibleLabels.remove(overlap);
                disabledLabels.push(overlap);
                removedLAmount++;
            }
            currentSolution.add(label);
            if (currentSolution.size() > bestSolution.size()) {
                bestSolution = currentSolution;
            }
            bruteForce(possibleLabels, currentSolution);
            currentSolution.remove(label);
            possibleLabels.add(label);
            while (removedLAmount > 0) {
                possibleLabels.add(disabledLabels.pop());
            }
        }
    }
    
}
