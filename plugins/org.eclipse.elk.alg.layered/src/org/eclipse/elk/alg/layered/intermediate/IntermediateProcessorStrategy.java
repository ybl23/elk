/*******************************************************************************
 * Copyright (c) 2010, 2016 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.alg.layered.intermediate;

import org.eclipse.elk.alg.layered.graph.LGraph;
import org.eclipse.elk.alg.layered.intermediate.compaction.HorizontalGraphCompactor;
import org.eclipse.elk.alg.layered.intermediate.wrapping.BreakingPointInserter;
import org.eclipse.elk.alg.layered.intermediate.wrapping.BreakingPointProcessor;
import org.eclipse.elk.alg.layered.intermediate.wrapping.BreakingPointRemover;
import org.eclipse.elk.alg.layered.intermediate.wrapping.PathLikeGraphWrapper;
import org.eclipse.elk.alg.layered.p3order.LayerSweepCrossingMinimizer;
import org.eclipse.elk.alg.layered.p3order.LayerSweepCrossingMinimizer.CrossMinType;
import org.eclipse.elk.core.alg.ILayoutProcessor;
import org.eclipse.elk.core.alg.ILayoutProcessorFactory;

/**
 * Definition of available intermediate layout processors for the layered layouter. This enumeration also serves as a
 * factory for intermediate layout processors.
 *
 * @author cds
 * @author ima
 * @kieler.design 2012-08-10 chsch grh
 * @kieler.rating proposed yellow by msp
 */
public enum IntermediateProcessorStrategy implements ILayoutProcessorFactory<LGraph> {

    /*
     * In this enumeration, intermediate layout processors are listed by the earliest slot in which
     * they can sensibly be used. The order in which they are listed is determined by the
     * dependencies on other processors.
     */

    // Before Phase 1

    /** Mirrors the graph to perform a right-to-left drawing. */
    LEFT_DIR_PREPROCESSOR,
    /** Transposes the graph to perform a top-bottom drawing. */
    DOWN_DIR_PREPROCESSOR,
    /** Mirrors and transposes the graph to perform a bottom-up drawing. */
    UP_DIR_PREPROCESSOR,
    /** Removes some comment boxes to place them separately in a post-processor. */
    COMMENT_PREPROCESSOR,
    /** Makes sure nodes with layer constraints have only incoming or only outgoing edges. */
    EDGE_AND_LAYER_CONSTRAINT_EDGE_REVERSER,
    /** Creates connected components for the SplineSelfLoopPre- and postprocessor. */
    SPLINE_SELF_LOOP_PREPROCESSOR,
    /** If one of the phases is set to interactive mode, this processor positions external ports. */
    INTERACTIVE_EXTERNAL_PORT_POSITIONER,
    /** Add constraint edges to respect partitioning of nodes. */
    PARTITION_PREPROCESSOR,
    
    // Before Phase 2

    /** Splits big nodes into multiple layers to distribute them better and reduce whitespace. */
    BIG_NODES_PREPROCESSOR,
    /** Adds dummy nodes in edges where center labels are present. */
    LABEL_DUMMY_INSERTER,

    // Before Phase 3
    
    /** Moves trees of high degree nodes to separate layers. */
    HIGH_DEGREE_NODE_LAYER_PROCESSOR,
    /** Remove partition constraint edges. */
    PARTITION_POSTPROCESSOR,
    /** Node-promotion for prettier graphs, especially algorithms like longest-path are prettified. */
    NODE_PROMOTION,
    /** Makes sure that layer constraints are taken care of. */
    LAYER_CONSTRAINT_PROCESSOR,
    /** Handles northern and southern hierarchical ports. */
    HIERARCHICAL_PORT_CONSTRAINT_PROCESSOR,
    /** Process layered big nodes, such that they are not interrupted by long edge nodes. */
    BIG_NODES_INTERMEDIATEPROCESSOR,
    /** Adds successor constraints between regular nodes before crossing minimization. */
    SEMI_INTERACTIVE_CROSSMIN_PROCESSOR,
    /** Inserts breaking points which are used to 'wrap' the graph after crossing minimization. */
    BREAKING_POINT_INSERTER,
    /** Takes a layered graph and turns it into a properly layered graph. */
    LONG_EDGE_SPLITTER,
    /** Makes sure nodes have at least fixed port sides. */
    PORT_SIDE_PROCESSOR,
    /** Tries to switch the label dummy nodes which the middle most dummy node of a long edge. */
    LABEL_DUMMY_SWITCHER,
    /** Tries to shorten labels where necessary. */
    LABEL_MANAGEMENT_PROCESSOR,
    /** Takes a layered graph and inserts dummy nodes for edges connected to inverted ports. */
    INVERTED_PORT_PROCESSOR,
    /** Takes care of self loops. */
    SELF_LOOP_PROCESSOR,
    /** Orders the port lists of nodes with fixed port order. */
    PORT_LIST_SORTER,
    /** Inserts dummy nodes to take care of northern and southern ports. */
    NORTH_SOUTH_PORT_PREPROCESSOR,

    // Before Phase 4
    /** Performs 'wrapping' of the graph, potentially executing improvement heuristics. */
    BREAKING_POINT_PROCESSOR,
    /** Hierarchical one-sided greedy switch crossing reduction. */
    ONE_SIDED_GREEDY_SWITCH,
    /** Hierarchical two-sided greedy switch crossing reduction. */
    TWO_SIDED_GREEDY_SWITCH,
    /** Unhide self loops after phase 3. */
    SPLINE_SELF_LOOP_POSITIONER,
    /** Wraps path-like graphs such that they better fit a given drawing area. */
    PATH_LIKE_GRAPH_WRAPPER,
    /** Makes sure that in-layer constraints are handled. */
    IN_LAYER_CONSTRAINT_PROCESSOR,
    /** Merges long edge dummy nodes belonging to the same hyperedge. */
    HYPEREDGE_DUMMY_MERGER,
    /** Decides, on which side of an edge the edge labels should be placed. */
    LABEL_SIDE_SELECTOR,
    /** Alternative big nodes handling, splitting nodes _after_ crossing minimization. */
    BIG_NODES_SPLITTER,
    /** Sets the positions of ports and labels, and sets the node sizes. */
    LABEL_AND_NODE_SIZE_PROCESSOR,
    /** Calculates the self loops with relative position to the parent node.*/
    SPLINE_SELF_LOOP_ROUTER,
    /** Calculates the margins of nodes according to the sizes of ports and labels. */
    NODE_MARGIN_CALCULATOR,
    /** Adjusts the width of hierarchical port dummy nodes. */
    HIERARCHICAL_PORT_DUMMY_SIZE_PROCESSOR,

    // Before Phase 5

    /** Fix coordinates of hierarchical port dummy nodes. */
    HIERARCHICAL_PORT_POSITION_PROCESSOR,
    /** Calculate the size of layers. */
    LAYER_SIZE_AND_GRAPH_HEIGHT_CALCULATOR,
    /** Merges dummy nodes originating from big nodes. */
    BIG_NODES_POSTPROCESSOR,

    // After Phase 5

    /** Reinserts and places comment boxes that have been removed before. */
    COMMENT_POSTPROCESSOR,
    /** Moves hypernodes horizontally for better placement. */
    HYPERNODE_PROCESSOR,
    /** Routes edges incident to hierarchical ports orthogonally. */
    HIERARCHICAL_PORT_ORTHOGONAL_EDGE_ROUTER,
    /** Takes a properly layered graph and removes the dummy nodes due to proper layering. */
    LONG_EDGE_JOINER,
    /** Removes the breaking points that were inserted for 'wrapping', derives edge routes correspondingly. */
    BREAKING_POINT_REMOVER,
    /** Removes dummy nodes inserted by the north south side preprocessor and routes edges. */
    NORTH_SOUTH_PORT_POSTPROCESSOR,
    /** Removes dummy nodes which were introduced for center labels. */
    LABEL_DUMMY_REMOVER,
    /** Moves nodes and vertical edge segments in horizontal direction to close some gaps that are a
      * result of the layering. */
    HORIZONTAL_COMPACTOR,
    /** Takes the reversed edges of a graph and restores their original direction. */
    REVERSED_EDGE_RESTORER,
    /** Place end labels on edges. */
    END_LABEL_PROCESSOR,
    /** In hierarchical graphs, maps a child graph to its parent node. */
    HIERARCHICAL_NODE_RESIZER,
    /** Mirrors the graph to perform a right-to-left drawing. */
    LEFT_DIR_POSTPROCESSOR,
    /** Transposes the graph to perform a top-bottom drawing. */
    DOWN_DIR_POSTPROCESSOR,
    /** Mirrors and transposes the graph to perform a bottom-up drawing. */
    UP_DIR_POSTPROCESSOR;

    /**
     * Creates an instance of the layout processor described by this instance.
     * 
     * @return the layout processor.
     */
    // SUPPRESS CHECKSTYLE NEXT MethodLength
    public ILayoutProcessor<LGraph> create() {
        switch (this) {

        case BIG_NODES_INTERMEDIATEPROCESSOR:
            return new BigNodesIntermediateProcessor();

        case BIG_NODES_POSTPROCESSOR:
            return new BigNodesPostProcessor();

        case BIG_NODES_PREPROCESSOR:
            return new BigNodesPreProcessor();

        case BIG_NODES_SPLITTER:
            return new BigNodesSplitter();
            
        case BREAKING_POINT_INSERTER:
            return new BreakingPointInserter();
            
        case BREAKING_POINT_PROCESSOR:
            return new BreakingPointProcessor();
            
        case BREAKING_POINT_REMOVER:
            return new BreakingPointRemover();
            

        case COMMENT_POSTPROCESSOR:
            return new CommentPostprocessor();

        case COMMENT_PREPROCESSOR:
            return new CommentPreprocessor();

        case DOWN_DIR_POSTPROCESSOR:
        case DOWN_DIR_PREPROCESSOR:
            return new GraphTransformer(GraphTransformer.Mode.TRANSPOSE);

        case EDGE_AND_LAYER_CONSTRAINT_EDGE_REVERSER:
            return new EdgeAndLayerConstraintEdgeReverser();

        case END_LABEL_PROCESSOR:
            return new EndLabelProcessor();

        case ONE_SIDED_GREEDY_SWITCH:
            return new LayerSweepCrossingMinimizer(CrossMinType.ONE_SIDED_GREEDY_SWITCH);
            
        case TWO_SIDED_GREEDY_SWITCH:
            return new LayerSweepCrossingMinimizer(CrossMinType.TWO_SIDED_GREEDY_SWITCH);

        case HIERARCHICAL_NODE_RESIZER:
            return new HierarchicalNodeResizingProcessor();

        case HIERARCHICAL_PORT_CONSTRAINT_PROCESSOR:
            return new HierarchicalPortConstraintProcessor();

        case HIERARCHICAL_PORT_DUMMY_SIZE_PROCESSOR:
            return new HierarchicalPortDummySizeProcessor();

        case HIERARCHICAL_PORT_ORTHOGONAL_EDGE_ROUTER:
            return new HierarchicalPortOrthogonalEdgeRouter();

        case HIERARCHICAL_PORT_POSITION_PROCESSOR:
            return new HierarchicalPortPositionProcessor();

        case HIGH_DEGREE_NODE_LAYER_PROCESSOR:
            return new HighDegreeNodeLayeringProcessor();

        case HORIZONTAL_COMPACTOR:
            return new HorizontalGraphCompactor();

        case HYPEREDGE_DUMMY_MERGER:
            return new HyperedgeDummyMerger();

        case HYPERNODE_PROCESSOR:
            return new HypernodesProcessor();

        case IN_LAYER_CONSTRAINT_PROCESSOR:
            return new InLayerConstraintProcessor();
            
        case INTERACTIVE_EXTERNAL_PORT_POSITIONER:
            return new InteractiveExternalPortPositioner();

        case LABEL_AND_NODE_SIZE_PROCESSOR:
            return new LabelAndNodeSizeProcessor();

        case LABEL_DUMMY_INSERTER:
            return new LabelDummyInserter();

        case LABEL_DUMMY_REMOVER:
            return new LabelDummyRemover();

        case LABEL_DUMMY_SWITCHER:
            return new LabelDummySwitcher();
            
        case LABEL_MANAGEMENT_PROCESSOR:
            return new LabelManagementProcessor();
            
        case LABEL_SIDE_SELECTOR:
            return new LabelSideSelector();

        case LAYER_CONSTRAINT_PROCESSOR:
            return new LayerConstraintProcessor();

        case LAYER_SIZE_AND_GRAPH_HEIGHT_CALCULATOR:
            return new LayerSizeAndGraphHeightCalculator();

        case LEFT_DIR_POSTPROCESSOR:
        case LEFT_DIR_PREPROCESSOR:
            return new GraphTransformer(GraphTransformer.Mode.MIRROR_X);

        case LONG_EDGE_JOINER:
            return new LongEdgeJoiner();

        case LONG_EDGE_SPLITTER:
            return new LongEdgeSplitter();

        case NODE_MARGIN_CALCULATOR:
            return new NodeMarginCalculator();
            
        case NODE_PROMOTION:
            return new NodePromotion();

        case NORTH_SOUTH_PORT_POSTPROCESSOR:
            return new NorthSouthPortPostprocessor();

        case NORTH_SOUTH_PORT_PREPROCESSOR:
            return new NorthSouthPortPreprocessor();

        case INVERTED_PORT_PROCESSOR:
            return new InvertedPortProcessor();

        case PARTITION_POSTPROCESSOR:
            return new PartitionPostprocessor();

        case PARTITION_PREPROCESSOR:
            return new PartitionPreprocessor();

        case PORT_LIST_SORTER:
            return new PortListSorter();

        case PORT_SIDE_PROCESSOR:
            return new PortSideProcessor();

        case REVERSED_EDGE_RESTORER:
            return new ReversedEdgeRestorer();

        case PATH_LIKE_GRAPH_WRAPPER:
            return new PathLikeGraphWrapper();

        case SELF_LOOP_PROCESSOR:
            return new SelfLoopProcessor();

        case SEMI_INTERACTIVE_CROSSMIN_PROCESSOR:
            return new SemiInteractiveCrossMinProcessor();

        case SPLINE_SELF_LOOP_POSITIONER:
            return new SplineSelfLoopPositioner();
            
        case SPLINE_SELF_LOOP_PREPROCESSOR:
            return new SplineSelfLoopPreProcessor();
            
        case SPLINE_SELF_LOOP_ROUTER:
            return new SplineSelfLoopRouter();
            
        case UP_DIR_POSTPROCESSOR:
        case UP_DIR_PREPROCESSOR:
            return new GraphTransformer(GraphTransformer.Mode.MIRROR_AND_TRANSPOSE);

        default:
            throw new IllegalArgumentException(
                    "No implementation is available for the layout processor " + toString());
        }
    }
}
