package planning;

import java.util.Comparator;
import java.util.Map;
import modelling.Variable;

/**
 * Classe StateComparator implémentant l'interface Comparator.
 * Cette classe est utilisée pour comparer la distance entre deux états.
 * Elle est généralement utilisée pour trier une collection d'états basée sur leur distance.
 */
public class StateComparator implements Comparator<Map<Variable, Object>> {

  // Map stockant la distance associée à chaque état.
  private Map<Map<Variable, Object>, Float> distance;

  /**
   * Constructeur de la classe StateComparator.
   * Initialise l'objet avec la Map des distances fournies.
   *
   * @param distance Map associant à chaque état (Map<Variable, Object>) une distance (Float).
   */
  public StateComparator(Map<Map<Variable, Object>, Float> distance) {
    this.distance = distance;
  }

  /**
   * Méthode de comparaison de deux états basée sur leur distance.
   * Cette méthode est utilisée lors du tri d'une collection d'états.
   *
   * @param map0 Premier état à comparer.
   * @param map1 Deuxième état à comparer.
   * @return     Retourne un nombre négatif, zéro, ou un nombre positif si la distance du premier état
   *             est respectivement inférieure, égale, ou supérieure à la distance du deuxième état.
   */
  @Override
  public int compare(Map<Variable, Object> map0, Map<Variable, Object> map1) {
    float distance0 = distance.get(map0); // Récupère la distance du premier état
    float distance1 = distance.get(map1); // Récupère la distance du deuxième état
    return Float.compare(distance0, distance1); // Compare les deux distances
  }

}
