package com.codecool.bfsexample;

import com.codecool.bfsexample.dao.UserNodeDAO;
import com.codecool.bfsexample.helpers.GraphPlotter;
import com.codecool.bfsexample.helpers.HibernateUtil;
import com.codecool.bfsexample.helpers.RandomDataGenerator;
import com.codecool.bfsexample.model.UserNode;

import javax.persistence.EntityManager;
import java.util.*;

public class BFSExample {

    public static void populateDB(UserNodeDAO userNodeDAO) {
        RandomDataGenerator generator = new RandomDataGenerator();
        List<UserNode> users = generator.generate();
        userNodeDAO.addAll(users);
    }

    public static void main(String[] args) {
        EntityManager em = HibernateUtil.getEnityManagerFactory().createEntityManager();
        UserNodeDAO userNodeDAO = new UserNodeDAO(em);
        em.clear();
        populateDB(userNodeDAO);

        List<UserNode> users = userNodeDAO.getAll();
        UserNode startUser = users.get(0);
        UserNode endUser = users.get(10);

        int distance = getDistance(startUser, endUser, users);
        LinkedList<UserNode> path = getPath(startUser, endUser, users);
        Set<UserNode> friendAtDistance = getFriendsAtDistance(startUser, distance, users);

        for (UserNode user : friendAtDistance) {
            System.out.println(user.getFirstName() + " " + user.getLastName());
        }
        System.out.println("Distance between " + startUser.getFirstName() + " " +
                startUser.getLastName() + " and " + endUser.getFirstName() +
                " " + endUser.getLastName() + " is " + distance + "\n\nThe path is:\n");
        StringBuilder sb = new StringBuilder();

        for(UserNode user : path) {
            sb.append(user.getFirstName()).append(" ").append(user.getLastName()).append(" - ");
        }

        System.out.println(sb.toString().trim());

        GraphPlotter.plot(users);
    }

    private static int getDistance(UserNode startNode, UserNode endNode, List<UserNode> nodes) {
        int endUserIndex = (int) endNode.getId() - 1;

        return getPath(startNode, endNode, nodes).size() - 1;
    }

    private static Set<UserNode> getFriendsAtDistance(UserNode startNode, int lookingDistance, List<UserNode> nodes) {
        int[] distances = getDistances(startNode, nodes);
        Set<UserNode> result = new HashSet<>();
        int userIndex = 1;

        for(int distance : distances) {
            if(distance == lookingDistance) {
                result.add(nodes.get(userIndex));
                userIndex++;
            }
        }

        return result;
    }

    private static int[] getDistances(UserNode startNode, List<UserNode> nodes) {
        int[] distances = new int[nodes.size()];
        Arrays.fill(distances, -1);
        LinkedList<UserNode> queue = new LinkedList<>();
        queue.add(startNode);
        int startNodeIndex = (int) startNode.getId()-1;
        distances[startNodeIndex] = 0;

        while(queue.size() > 0) {
            UserNode currentNode = queue.poll();
            int currentNodeIndex = (int) currentNode.getId() - 1;

            for(UserNode child : currentNode.getFriends()) {
                int childIndex = (int) child.getId() -1;
                if(distances[childIndex] == -1) {
                    queue.add(child);
                    distances[childIndex] = distances[currentNodeIndex] + 1;
                }
            }
        }

        return distances;
    }

    private static LinkedList<UserNode> getPath(UserNode startUser, UserNode endUser, List<UserNode> users) {
        LinkedList<UserNode>[] paths = new LinkedList[users.size()];
        LinkedList<UserNode> queue = new LinkedList<>();
        queue.add(startUser);
        int startUserIndex = (int) startUser.getId() - 1;
        paths[startUserIndex] = new LinkedList<>();
        paths[startUserIndex].add(startUser);

        while(queue.size() > 0) {
            UserNode currentUser = queue.poll();
            int currentUserIndex = (int) currentUser.getId() - 1;

            for(UserNode childUser : currentUser.getFriends()) {
                int childUserIndex = (int) childUser.getId() - 1;
                if(paths[childUserIndex] == null) {
                    queue.add(childUser);
                    paths[childUserIndex] = (LinkedList<UserNode>) paths[currentUserIndex].clone();
                    paths[childUserIndex].add(childUser);
                }
            }
        }

        int endUserIndex = (int) endUser.getId() - 1;

        return paths[endUserIndex];
    }
}