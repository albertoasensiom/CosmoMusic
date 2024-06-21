package com.mygdx.mygame;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;

public class Piano extends JPanel {

    private static final long serialVersionUID = 1L;

    public Piano() {
        setForeground(new Color(0, 0, 0));
        setBackground(new Color(240, 240, 240));
        setLayout(null);

        JButton TileCSharp = new JButton("C#");
        TileCSharp.setBounds(54, 11, 47, 103);
        add(TileCSharp);

        JButton TileDSharp = new JButton("D#");
        TileDSharp.setBounds(119, 11, 47, 103);
        add(TileDSharp);

        JButton TileFSharp = new JButton("F#");
        TileFSharp.setBounds(252, 11, 47, 103);
        add(TileFSharp);

        JButton TileGSharp = new JButton("G#");
        TileGSharp.setBounds(321, 11, 47, 103);
        add(TileGSharp);

        JButton TileASharp = new JButton("A#");
        TileASharp.setBounds(392, 11, 47, 103);
        add(TileASharp);

        JButton TileC = new JButton("C");
        TileC.setBounds(10, 11, 67, 167);
        add(TileC);

        JButton TileD = new JButton("D");
        TileD.setBounds(76, 11, 67, 167);
        add(TileD);

        JButton TileE = new JButton("E");
        TileE.setBounds(142, 11, 67, 167);
        add(TileE);

        JButton TileF = new JButton("F");
        TileF.setBounds(208, 11, 67, 167);
        add(TileF);

        JButton TileG = new JButton("G");
        TileG.setBounds(271, 11, 67, 167);
        add(TileG);

        JButton TileA = new JButton("A");
        TileA.setBounds(337, 11, 67, 167);
        add(TileA);

        JButton TileB = new JButton("B");
        TileB.setBounds(403, 11, 67, 167);
        add(TileB);

        JButton TileCHigh = new JButton("C high");
        TileCHigh.setBounds(469, 11, 67, 167);
        add(TileCHigh);
    }
}
