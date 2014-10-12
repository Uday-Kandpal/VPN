package gui;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author Uday Kandpal
 */
public interface Displayable {

    public JProgressBar getProgressBar();

    public JPanel getDisplayForm();

    public interface Form extends Displayable {

        public JList getList();

        public Field getFieldById(int id);

        public Field getFieldByName(String name);

        public interface Field extends Form {

            public void getId();

            public void getName();

            public String getContext();

            public String getData();

            public interface Button extends Field {

                public boolean isSubmitButton();

                public boolean isCancelButton();

            }
        }

    }

}
