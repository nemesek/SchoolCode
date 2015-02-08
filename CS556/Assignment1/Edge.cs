using System;

namespace Assignment1
{
    // E = Edge Label
    // V = Vertex Identifier
    // L = Vertex Label
    public class Edge<E,V,L>
    {
        private readonly E _label;
        private readonly Tuple<Vertex<V,L>, Vertex<V,L>> _arc;

        public Edge(E label, Vertex<V,L> predecessor, Vertex<V,L> successor)
        {
            if (predecessor == null) throw new ArgumentNullException("predecessor");
            if (successor == null) throw new ArgumentNullException("successor");

            _label = label;
            _arc = new Tuple<Vertex<V,L>, Vertex<V,L>>(predecessor, successor);
        }

        public E Label { get { return _label; } }
        public Vertex<V,L> DirectPredecessor { get { return _arc.Item1; } }
        public Vertex<V,L> DirectSuccessor { get { return _arc.Item2; } }
    }
}
