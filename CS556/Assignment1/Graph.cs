﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq;

namespace Assignment1
{
    public class Graph<T>
    {
        private List<Tuple<Vertex<T>, List<Vertex<T>>>> _adjacencyList = new List<Tuple<Vertex<T>, List<Vertex<T>>>>();
        public Graph()
        {

        }

        public bool IsEmpty
        {
            get { return _adjacencyList.Count == 0; }

        }

        public Graph<T> AddVertex(Vertex<T> vertex, T label)
        {
            vertex.Label = label;
            var tupleToAdd = new Tuple<Vertex<T>, List<Vertex<T>>>(vertex, new List<Vertex<T>>());
            _adjacencyList.Add(tupleToAdd);
            return this;
        }

        public Graph<T> RemoveVertex(Vertex<T> vertex)
        {
            // todo: delete all edges
            var vertexToRemove = _adjacencyList
                .Where(t => t.Item1.Label.Equals(vertex.Label))
                .SingleOrDefault();

            if (vertexToRemove != null) _adjacencyList.Remove(vertexToRemove);
            
            return this;
        }


        public Graph<T> UpdateVertex(Vertex<T> vertex, T label)
        {
            var vertexToUpdate = _adjacencyList
                .Where(t => t.Item1.Label.Equals(vertex.Label))
                .SingleOrDefault();

            if (vertexToUpdate != null)
            {
                vertexToUpdate.Item1.Label = label;
            }

            return this;
        }

        public T GetVertex(Vertex<T> vertex)
        {
            var vertexToGet = this.GetVertex(vertex.Label);
            if (vertexToGet == null) return default(T);
            return vertexToGet.Item1.Label;

        }

        private Tuple<Vertex<T>, List<Vertex<T>>> GetVertex(T label)
        {
            var vertexToGet = _adjacencyList
                .Where(t => t.Item1.Label.Equals(label))
                .SingleOrDefault();

            return vertexToGet;
        }

        public bool HasVertex(Vertex<T> vertex)
        {

            var hasVertex = _adjacencyList
                .Where(t => t.Item1.Label.Equals(vertex.Label))
                .SingleOrDefault() != null ? true : false;

            return hasVertex;
        }

        public IEnumerable<Vertex<T>> AllVertices()
        {
            return _adjacencyList
                .Select(t => t.Item1)
                .ToList();
        }

        public Graph<T> AddEdge(Vertex<T> vertex1, Vertex<T> vertex2)
        {
            var source = this.GetVertex(vertex1.Label);
            if (source == null) return this;    // todo: figure out what to return

            var destination = this.GetVertex(vertex2.Label);
            if (destination == null) return this;

            source.Item2.Add(vertex2);
            return this;
            
        }

        public bool HasEdge(Vertex<T> vertex1, Vertex<T> vertex2)
        {
            var source = this.GetVertex(vertex1.Label);
            if(source == null) return false;

            var destination = this.GetVertex(vertex2.Label);
            if (destination == null) return false;

            return source.Item2.Where(v => v.Label.Equals(vertex2.Label)).Count() > 0;
        }
    }
}
