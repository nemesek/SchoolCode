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

        public Edge(E label, Vertex<V,L> source, Vertex<V,L> destination)
        {
            _label = label;
            _arc = new Tuple<Vertex<V,L>, Vertex<V,L>>(source, destination);
        }

        public E Label { get { return _label; } }
        public Vertex<V,L> DirectPredecessor { get { return _arc.Item1; } }
        public Vertex<V,L> DirectSuccessor { get { return _arc.Item2; } }
        
    }
}
