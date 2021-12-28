package ag.pinguin.issuetracker.planner.service;

import ag.pinguin.issuetracker.issue.domain.Issue;
import ag.pinguin.issuetracker.issue.domain.Story;
import ag.pinguin.issuetracker.planner.dto.IssueGroupDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Issue Grouping Service Implementation Based On Max Point Value Per Developer
 *
 * @author Mehdi Chitforoosh
 */
@Service
public class DefaultIssueGroupingService implements IssueGroupingService {

    @Override
    public List<IssueGroupDto> findIssueGroups(List<Issue> issues, Integer maxPointPerDeveloper) {
        // Create graph nodes
        var graphNodes = new HashMap<Long, GraphNode>();
        issues.stream()
                .map(issue -> (Story) issue)
                .forEach(story -> graphNodes.put(story.getIssueId(), new GraphNode(story, story.getEstimatedPoint())));
        var issueGroupDtos = new ArrayList<IssueGroupDto>();
        // Following loop invokes DFS algorithm
        graphNodes.forEach((issueId, graphNode) -> {
            var sum = new AtomicInteger(0);
            if (!graphNode.isSelected()) {
                // Recursive call to the DFS algorithm
                var graphNodeList = depthFirst(issueId, graphNodes, sum, maxPointPerDeveloper);
                var selectedIssues = graphNodeList.stream().map(node -> node.issue).collect(Collectors.toList());
                // Add nodes group with sum of point values less than or equal to max point value
                issueGroupDtos.add(new IssueGroupDto(selectedIssues));
            }
        });
        return issueGroupDtos;
    }

    /**
     * Recursive depth first search (DFS) to find optimal paths
     *
     * @param issueId
     * @param graphNodes
     * @param sum
     * @param maxPointPerDeveloper
     * @return
     */
    private List<GraphNode> depthFirst(Long issueId, Map<Long, GraphNode> graphNodes, AtomicInteger sum, Integer maxPointPerDeveloper) {
        var currentGraphNode = graphNodes.get(issueId);
        var currentPointValue = currentGraphNode.getPointValue();
        var sumOfPointValues = currentPointValue + sum.get();
        if (sumOfPointValues < maxPointPerDeveloper) {
            // Marking the selected node as true
            currentGraphNode.setSelected(true);
            // Updating the value of sum
            sum.addAndGet(currentPointValue);
            var list = new ArrayList<GraphNode>();
            list.add(currentGraphNode);
            // Traverse for all adjacent nodes
            for (Map.Entry<Long, GraphNode> entry : graphNodes.entrySet()) {
                var issId = entry.getKey();
                var graphNode = entry.getValue();
                if (!graphNode.isSelected()) {
                    // Recursive call to the DFS algorithm
                    var graphNodeList = depthFirst(issId, graphNodes, sum, maxPointPerDeveloper);
                    list.addAll(graphNodeList);
                    // Get first list and then return, It can be optimized based on some parameters like list size, sum of point values, ...
                    break;
                }
            }
            return list;
        } else if (sumOfPointValues == maxPointPerDeveloper) {
            // Marking the selected vertex as true
            currentGraphNode.setSelected(true);
            sum.addAndGet(currentPointValue);
            return Collections.singletonList(currentGraphNode);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Graph Node to keep states
     */
    private class GraphNode {

        private final Issue issue;
        private final Integer pointValue;
        private boolean selected;

        public GraphNode(Issue issue, Integer pointValue) {
            this.issue = issue;
            this.pointValue = pointValue;
        }

        public Issue getIssue() {
            return issue;
        }

        public Integer getPointValue() {
            return pointValue;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isSelected() {
            return selected;
        }
    }
}
