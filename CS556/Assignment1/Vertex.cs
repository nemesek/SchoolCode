using System;

namespace Assignment1
{
    // V = Vertex Identifier
    // L = Vertex Label
    public class Vertex<V,L>
    {
        private readonly V _identifier;
        private readonly L _label;

        public Vertex(V identifier)
        {
            if (!typeof(V).IsValueType && identifier == null) throw new ArgumentException("identifier");
            _identifier = identifier;
            _label = default(L);
        }

        public Vertex(V identifier, L label)
        {
            if (!typeof(V).IsValueType && identifier == null) throw new ArgumentException("identifier");
            _identifier = identifier;
            _label = label;
        }

        public V Identifier { get { return _identifier; } }
        public L Label { get { return _label; } }
    }
}
