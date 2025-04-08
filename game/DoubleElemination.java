package game;

import java.util.*;

public class DoubleElemination {
    private final int countPlayers;
    private ArrayList<Player> players;
    private int m;
    private int n;
    private int k;
    private String typeBoard;
    //note: list
    public DoubleElemination(int countPlayers, ArrayList<Player> players, int m, int n, int k, String typeBoard){
        this.countPlayers = countPlayers;
        this.players = players;
        this.m = m;
        this.n = n;
        this.k = k;
        this.typeBoard = typeBoard;
    }
    public int getResult (Player player1, Player player2){
        if (typeBoard.equals("Diamond")){
            return new Game(true, player1, player2).play(new MNKBoard(m, n, k, true));
        }
        return new Game(true, player1, player2).play(new MNKBoard(m, n, k, true));
    }
    public int findLog(int n){
        int a = 1;
        while (a <= n){
            a *= 2;
        }
        return n - a / 2;
    }
    public int findMinPow(int n){
        int a = 1;
        while (a <= n){
            a <<= 1;
        }
        return a / 4;
    }
    public void statements(ArrayList<Player> outsiders, ArrayList<Player> topTable, ArrayList<Player> downTable, HashMap<Player, String> numberOfPlayers){
        System.out.println();
        System.out.println("Top table of our Game. These guys has no one lose");
        for (Player player : topTable) {
            System.out.println(numberOfPlayers.get(player));
        }
        System.out.println();
        System.out.println("Down table of out Game. These guys has only one lose, but they're no margin for error");
        for (Player player : downTable) {
            System.out.println(numberOfPlayers.get(player));
        }
        System.out.println();
        System.out.println("Losers of our Game. They're watching this tournament on TV and ready to go to the airport.");
        for (Player outsider : outsiders) {
            System.out.println(numberOfPlayers.get(outsider));
        }
        System.out.println();
    }
    public void playRoundInTop(ArrayList<Player> outsiders, ArrayList<Player> topTable, ArrayList<Player> downTable, HashMap<Player, String> numberOfPlayers){
        int countLuckyPlayers = findLog(topTable.size());
        Collections.shuffle(topTable);
        ArrayList<Player> outOfTop = new ArrayList<>();
        for (int i = countLuckyPlayers + 1; i < topTable.size(); i += 2){
            Player player1 = topTable.get(i), player2 = topTable.get(i - 1);
            System.out.println("Now playing " + numberOfPlayers.get(player1) + " vs " + numberOfPlayers.get(player2) + " in top table.");
            //note: copoypaste
            Game game = new Game(true, player1, player2);
            int result = getResult(player1, player2);
            int countOfDraws = 0;
            while (result == 0 && countOfDraws < 2){
                countOfDraws++;
                result = getResult(player1, player2);
            }
            if (result == 0){
                System.out.println("You are cheating. Both players go out in down table. Bye-bye losers");
                outOfTop.add(player1);
                outOfTop.add(player1);
            }
            if (result == 1){
                System.out.println(numberOfPlayers.get(player1) +  " win. Congratulations, you pass on. " + numberOfPlayers.get(player2) + " drop out to down table.");
                outOfTop.add(player2);
            }
            if (result == 2){
                System.out.println(numberOfPlayers.get(player2) +  " win. Congratulations, you pass on. " + numberOfPlayers.get(player1) + " drop out to down table.");
                outOfTop.add(player1);
            }
        }
        for (Player player : outOfTop) {
            downTable.add(player);
            topTable.remove(player);
        }
        statements(outsiders, topTable, downTable, numberOfPlayers);
    }
    public void playRoundInDown(ArrayList<Player> outsiders, ArrayList<Player> topTable, ArrayList<Player> downTable, HashMap<Player, String> numberOfPlayers) {
        while (downTable.size() > findMinPow(topTable.size()) && downTable.size() > 1) {
            int countLuckyPlayers = findLog(downTable.size());
            Collections.shuffle(downTable);
            ArrayList<Player> outOfDown = new ArrayList<>();
            for (int i = countLuckyPlayers + 1; i < downTable.size(); i += 2) {
                Player player1 = downTable.get(i), player2 = downTable.get(i - 1);
                System.out.println("Now playing " + numberOfPlayers.get(player1) + " vs " + numberOfPlayers.get(player2) + " in down table.");
                Game game = new Game(true, player1, player2);
                int result = getResult(player1, player2);
                int countOfDraws = 0;
                while (result == 0 && countOfDraws < 2) {
                    countOfDraws++;
                    result = getResult(player1, player2);
                }
                if (result == 0) {
                    System.out.println("You are cheating. Both players go out from game. Bye-bye losers");
                    outOfDown.add(player1);
                    outOfDown.add(player1);
                }
                if (result == 1) {
                    System.out.println(numberOfPlayers.get(player1) +  " win. Congratulations, you pass on. " + numberOfPlayers.get(player2) + " drop out of game.");
                    outOfDown.add(player2);
                }
                if (result == 2) {
                    System.out.println(numberOfPlayers.get(player2) +  " win. Congratulations, you pass on. " + numberOfPlayers.get(player1) + " drop out of game.");
                    outOfDown.add(player1);
                }
            }
            for (Player player : outOfDown) {
                outsiders.add(player);
                downTable.remove(player);
            }
            statements(outsiders, topTable, downTable, numberOfPlayers);
        }
    }
    public void play(){
        HashMap<Player, String> numberOfPlayers = new HashMap<>();
        for (int i = 0; i < countPlayers; i++){
            numberOfPlayers.put(players.get(i), "Player" + (i + 1));
        }
        ArrayList<Player> outsiders = new ArrayList<>(), topTable = players, downTable = new ArrayList<>();
        statements(outsiders, topTable, downTable, numberOfPlayers);
        while (topTable.size() > 1) {
            playRoundInTop(outsiders, topTable, downTable, numberOfPlayers);
            playRoundInDown(outsiders, topTable, downTable, numberOfPlayers);
        }
        Player finalist1 = topTable.getFirst(), finalist2 = downTable.getFirst();
        System.out.println("Okaay, we have to finalists! It is " + numberOfPlayers.get(finalist1) + " and " + numberOfPlayers.get(finalist2));
        System.out.println("And we will know winner just a few seconds!");
        final Game game = new Game(true, finalist1, finalist2);
        int result = getResult(finalist1, finalist2);
        int games = 0;
        while (games < 2 && result == 0){
            games++;
            System.out.println("This game don't reveal a winner. Let's play another one!");
            result = getResult(finalist1, finalist2);
        }
        if (games == 2 && result == 0){
            System.out.println("After three games, who result was draw, we decide that the winner will be " + numberOfPlayers.get(finalist2) + " because playing for zero's is harder");
        }
        if (result == 1){
            System.out.println("And we have a winner! " + numberOfPlayers.get(finalist1));
        }
        if (result == 2){
            System.out.println("And we have a winner! " + numberOfPlayers.get(finalist2));
        }
    }
}
