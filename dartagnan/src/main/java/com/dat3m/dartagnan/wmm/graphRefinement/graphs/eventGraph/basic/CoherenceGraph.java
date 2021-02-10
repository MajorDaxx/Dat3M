package com.dat3m.dartagnan.wmm.graphRefinement.graphs.eventGraph.basic;

import com.dat3m.dartagnan.wmm.graphRefinement.ModelContext;
import com.dat3m.dartagnan.wmm.graphRefinement.coreReason.CoLiteral;
import com.dat3m.dartagnan.wmm.graphRefinement.coreReason.CoreLiteral;
import com.dat3m.dartagnan.wmm.graphRefinement.decoration.Edge;
import com.dat3m.dartagnan.wmm.graphRefinement.decoration.EventData;
import com.dat3m.dartagnan.wmm.graphRefinement.util.EdgeDirection;
import com.dat3m.dartagnan.wmm.graphRefinement.graphs.eventGraph.AbstractEventGraph;
import com.dat3m.dartagnan.wmm.graphRefinement.graphs.eventGraph.EventGraph;
import com.dat3m.dartagnan.wmm.graphRefinement.graphs.eventGraph.SimpleGraph;
import com.dat3m.dartagnan.wmm.graphRefinement.logic.Conjunction;
import com.dat3m.dartagnan.wmm.graphRefinement.graphs.timeable.Timestamp;

import java.util.*;
import java.util.stream.Collectors;

//A non-transitive version of coherence.
// The fact that it is coherence is only relevant for <computeReason>
public class CoherenceGraph extends AbstractEventGraph {

    @Override
    public List<EventGraph> getDependencies() {
        return Collections.emptyList();
    }

    private final SimpleGraph graph;

    public CoherenceGraph() {
        graph = new SimpleGraph();
    }

    @Override
    public boolean contains(Edge edge) {
        return graph.contains(edge);
    }

    @Override
    public Timestamp getTime(Edge edge) {
        return graph.getTime(edge);
    }

    @Override
    public int getMinSize() {
        return graph.getMinSize();
    }

    @Override
    public int getMaxSize() {
        return graph.getMaxSize();
    }

    @Override
    public int getMinSize(EventData e, EdgeDirection dir) {
        return graph.getMinSize(e, dir);
    }

    @Override
    public int getMaxSize(EventData e, EdgeDirection dir) {
        return graph.getMaxSize(e, dir);
    }

    @Override
    public boolean contains(EventData a, EventData b) {
        return graph.contains(a, b);
    }

    @Override
    public Timestamp getTime(EventData a, EventData b) {
        return graph.getTime(a, b);
    }


    @Override
    public void initialize(ModelContext context) {
        super.initialize(context);
        graph.initialize(context);
    }

    @Override
    public void backtrack() {
        graph.backtrack();
    }

    @Override
    public Collection<Edge> forwardPropagate(EventGraph changedGraph, Collection<Edge> addedEdges) {
        return forwardPropagate(addedEdges);
    }

    public Collection<Edge> forwardPropagate(Collection<Edge> addedEdges) {
        return addedEdges.stream().filter(graph::add).collect(Collectors.toList());
    }
    @Override
    public Iterator<Edge> edgeIterator() {
        return graph.edgeIterator();
    }

    @Override
    public Iterator<Edge> edgeIterator(EventData e, EdgeDirection dir) {
        return graph.edgeIterator(e, dir);
    }

    @Override
    public Conjunction<CoreLiteral> computeReason(Edge edge) {
        return contains(edge) ? new Conjunction<>(new CoLiteral(edge)) : Conjunction.FALSE;
    }
}