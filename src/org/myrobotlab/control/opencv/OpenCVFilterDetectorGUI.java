/**
 *                    
 * @author greg (at) myrobotlab.org
 *  
 * This file is part of MyRobotLab (http://myrobotlab.org).
 *
 * MyRobotLab is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version (subject to the "Classpath" exception
 * as provided in the LICENSE.txt file that accompanied this code).
 *
 * MyRobotLab is distributed in the hope that it will be useful or fun,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * All libraries in thirdParty bundle are subject to their own license
 * requirements - please refer to http://myrobotlab.org/libraries for 
 * details.
 * 
 * Enjoy !
 * 
 * */

package org.myrobotlab.control.opencv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.myrobotlab.opencv.FilterWrapper;
import org.myrobotlab.opencv.OpenCVFilterDetector;
import org.myrobotlab.service.GUIService;

public class OpenCVFilterDetectorGUI extends OpenCVFilterGUI implements ActionListener {

  String watchText = "watch foreground";
  String learnText = "learn background";
  JButton learn = new JButton(watchText);

  public OpenCVFilterDetectorGUI(String boundFilterName, String boundServiceName, GUIService myService) {
    super(boundFilterName, boundServiceName, myService);

    display.add(learn);
    learn.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object o = e.getSource();
    OpenCVFilterDetector bf = (OpenCVFilterDetector) boundFilter.filter;
    if (o == learn) {
      if (watchText.equals(learn.getText())) {
        learn.setText(learnText);
        bf.learningRate = 0;
      } else {
        learn.setText(watchText);
        bf.learningRate = -1;
      }
    }

  }

  // FIXME - update components :)
  @Override
  public void getFilterState(final FilterWrapper filterWrapper) {
    boundFilter = filterWrapper;
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        OpenCVFilterDetector bf = (OpenCVFilterDetector) filterWrapper.filter;
        if (bf.learningRate == -1) {
          learn.setText(watchText);
        } else {
          learn.setText(learnText);
        }
      }
    });

  }

}
