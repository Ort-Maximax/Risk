package com.ort.risk.storage;

import com.ort.risk.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.InputStream;


/**
 * @author CS
 * Parse the XML file into the Game object
 */
public class Parser {
    public static void prepMap() {

    	MapFileHandler mapHandler = new MapFileHandler();

        Game gameObj = Game.getInstance();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        /* TODO : La map doit etre importer d'apres un input de l'utilisateur, pas en dur*/
        InputStream in;
        String mapPath = null;
        Document xml;

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();


            File mapXML = mapHandler.getCurrentMapFile();
            xml = builder.parse(mapXML);
            /*
            if(gameObj.getExMode() == Launcher.ExecMode.GUI.value()){
                File mapXML = mapHandler.getCurrentMapFile();
				xml = builder.parse(mapXML);
            } else {
                in = Parser.class.getResourceAsStream("classic.xml");
                xml = builder.parse(in);
            }*/


            Element root = xml.getDocumentElement();
            XPathFactory xpf = XPathFactory.newInstance();
            XPath path = xpf.newXPath();

            //Set map's Img, name, minimal and divider
            setMapParams(gameObj, path, root);

            //Set map's modes
            setMapModes(gameObj, path, root);

            //Set map's zone, that's the big boy function
            setMapZones(gameObj, path, root);

            /* TODO : L'utilisateur choisis le modequ'il souhaite jouer*/

            /* TODO : On set le flag selected sur le mode selectionné*/

            /* TODO : On crée le nombre de Player correspondant au nb du mode*/

            //Here we got a complete map object, and ready to play
            //System.out.println(gameObj.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    protected static void setMapParams(Game gameObj, XPath path, Element root) {
        try {

            // Set the map's name
            String nameStr = (String) path.evaluate("/map/name", root);
            gameObj.setName(nameStr);

            //Set the map's img url
            String imgStr = (String) path.evaluate("/map/image", root);
            gameObj.setImg(imgStr);


            //Set the map's minimal reinforcment
            Integer nbMin = Integer.parseInt((path.evaluate("/map/minimal", root)).trim());
            gameObj.setNbMinReinforcement(nbMin);

            //Set the map's divider
            Integer nbDiv = Integer.parseInt((path.evaluate("/map/divisor", root)).trim());
            gameObj.setDivider(nbDiv);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected static void setMapModes(Game gameObj, XPath path, Element root) {

        try {
            //Get all <mode> from <modes>
            String modesExpr = "modes//mode";
            NodeList modesList = (NodeList) path.evaluate(modesExpr, root, XPathConstants.NODESET);

            //For each <mode>
            for (int i = 0; i < modesList.getLength(); i++) {
                Mode objMode = new Mode();
                Node mode = modesList.item(i);

                //Set number of player
                Integer nbPlayers = Integer.parseInt((path.evaluate("players", mode)).trim());
                objMode.setNbPlayer(nbPlayers);

                //Set the number of init troops
                Integer nbInit = Integer.parseInt((path.evaluate("initial", mode)).trim());
                objMode.setNbInitTroops(nbInit);

                //Add the mode to the map object
                //gameObj.toString();
                gameObj.addMode(objMode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    protected static void setMapZones(Game gameObj, XPath path, Element root) {

        try {
            //Get all <zone> from <zones>
            String zonesExpr = "zones//zone";
            NodeList zonesList = (NodeList) path.evaluate(zonesExpr, root, XPathConstants.NODESET);

            //For each <zone>
            for (int i = 0; i < zonesList.getLength(); i++) {
                Zone objZone = new Zone();
                Node zone = zonesList.item(i);

                //Set zone's name
                String zName = path.evaluate("name", zone);
                objZone.setName(zName);

                //Set zone's bonus
                Integer zBonus = Integer.parseInt((path.evaluate("bonus", zone)).trim());
                objZone.setBonus(zBonus);

                NodeList zoneRegionList = (NodeList) path.evaluate("regions/region", zone, XPathConstants.NODESET);

                // For each zone's region
                for (int j = 0; j < zoneRegionList.getLength(); j++) {
                    Node region = zoneRegionList.item(j);
                    Region objRegion = new Region();
                    objRegion.setIsOccupied(false);

                    //Set region's name
                    String rName = path.evaluate("name", region).trim();


                    //Get the regions from the <map><regions><region> so we can get <bonus>
                    NodeList regionRoot = (NodeList) path.evaluate("regions/region", root, XPathConstants.NODESET);
                    for (int mr = 0; mr < regionRoot.getLength(); mr++) {
                        Node matchedRegion = regionRoot.item(mr);
                        String matchedRegionName = path.evaluate("name", matchedRegion).trim();
                        String matchedRegionBonus = path.evaluate("bonus", matchedRegion).trim();
                        if (matchedRegionName.trim().equals(rName)) {
                            objRegion.setName(matchedRegionName);
                            objRegion.setBonus(Integer.parseInt(matchedRegionBonus));
                        }
                    }


                    //System.out.println(region.getNodeName() + " : " + region.getTextContent());


                    Node adjRoot = (Node) path.evaluate("adjacencies", root, XPathConstants.NODE);
                    //System.out.println(adjRoot.getTextContent());

                    //Look for region's corresponding adjacency object
                    String adjExpr = "//adjacency";
                    NodeList matchedAdjList = (NodeList) path.evaluate(adjExpr, adjRoot, XPathConstants.NODESET);
                    /*

                    // ATTENTION
                    // Ca marche SEULEMENT car les Adj sont dans le meme ordre que les regions
                    Node matchedAdj = matchedAdjList.item(j);
                    System.out.println(matchedAdj.getTextContent());

                    */

                    // Plutot nul a chier et pas rapide mais j'ai pas trouver mieu
                    // On devrais pouvoir matcher directement l'adjacency par rapport a la valeur de <start><region><name>
                    // mais j'y suis pas arrivé

                    // For every adjacency node
                    for (int k = 0; k < matchedAdjList.getLength(); k++) {
                        Node matchedAdj = matchedAdjList.item(k);
                        Node matchedAdjStartRegionName = (Node) path.evaluate("start/region/name", matchedAdj, XPathConstants.NODE);
                        // If the adjacency strat match with the current iteration's region name
                        if (matchedAdjStartRegionName.getTextContent().trim().equals(rName)) {

                            NodeList endList = (NodeList) path.evaluate("ends//end", matchedAdj, XPathConstants.NODESET);

                            // For every end region of the matched adjacency
                            for (int l = 0; l < endList.getLength(); l++) {

                                // Create Frontier object
                                Node end = endList.item(l);
                                Frontier objFrontier = new Frontier();

                                Node endRegionNameNode = (Node) path.evaluate("region/name", end, XPathConstants.NODE);
                                String endRegionName = endRegionNameNode.getTextContent().trim();

                                objFrontier.setRegionEnd(endRegionName);

                                NodeList endRegionMoves = (NodeList) path.evaluate("moves//move", end, XPathConstants.NODESET);
                                for (int m = 0; m < endRegionMoves.getLength(); m++) {
                                    Node move = endRegionMoves.item(m);
                                    Move objMove = new Move();
                                    Node moveNameNode = (Node) path.evaluate("name", move, XPathConstants.NODE);
                                    String moveName = moveNameNode.getTextContent().trim();
                                    objMove.setName(moveName);
                                    objFrontier.addMove(objMove);
                                }
                                objRegion.addFrontier(objFrontier);
                            }

                            objZone.addRegion(objRegion);
                            gameObj.addRegion(objRegion);
                        }
                    }
                }
                gameObj.addZone(objZone);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}