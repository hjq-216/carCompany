package lab3.database.course.frame;

import lab3.database.course.dao.Car;
import lab3.database.course.dao.Customer;
import lab3.database.course.sqltools.BorrowTools;
import lab3.database.course.sqltools.CustomerTools;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

public class Return_CarFrame extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLayeredPane layeredPane;

    private JScrollPane carScrollPane;
    private JTable carJtable;
    private DefaultTableModel defaultModel;
    public static int row;

    private JLabel idCustomerLabel;
    private JLabel nameCustomerLabel;
    private JLabel passwordLabel;

    private JLabel showidCustomerLabel;
    private JLabel showNameCustomerLabel;
    private JLabel showPasswordLabel;

    private JButton return_BookButton;

    public Return_CarFrame() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 996, 699);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton borrow_Button = new JButton("借车");
        borrow_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Search_CarFrame search_carFrame = new Search_CarFrame();
                search_carFrame.setVisible(true);
                CloseFrame();
            }
        });
        borrow_Button.setBounds(77, 288, 123, 29);
        contentPane.add(borrow_Button);

        JButton self_info_Button = new JButton("还车");
        self_info_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Return_CarFrame return_carFrame = new Return_CarFrame();
                return_carFrame.setVisible(true);
                CloseFrame();
            }
        });
        self_info_Button.setBounds(77, 474, 123, 29);
        contentPane.add(self_info_Button);

        JButton log_out_Button = new JButton("登出");
        log_out_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginframe = new LoginFrame();
                loginframe.setVisible(true);
                CloseFrame();
            }
        });
        log_out_Button.setBounds(817, 102, 85, 29);
        contentPane.add(log_out_Button);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(128, 61, 862, 598);
        contentPane.add(layeredPane);

        idCustomerLabel = new JLabel("卡号");
        idCustomerLabel.setFont(new Font("宋体", Font.PLAIN, 30));
        idCustomerLabel.setBounds(311, 102, 158, 43);
        layeredPane.add(idCustomerLabel);

        nameCustomerLabel = new JLabel("姓名");
        nameCustomerLabel.setFont(new Font("宋体", Font.PLAIN, 30));
        nameCustomerLabel.setBounds(241, 171, 81, 32);
        layeredPane.add(nameCustomerLabel);

        passwordLabel = new JLabel("密码");
        passwordLabel.setFont(new Font("宋体", Font.PLAIN, 30));
        passwordLabel.setBounds(241, 218, 81, 38);
        layeredPane.add(passwordLabel);

        showidCustomerLabel = new JLabel();
        showidCustomerLabel.setFont(new Font("宋体", Font.PLAIN, 30));
        showidCustomerLabel.setBounds(491, 102, 146, 43);
        layeredPane.add(showidCustomerLabel);
        showidCustomerLabel.setText(LoginFrame.userID);

        showNameCustomerLabel = new JLabel("");
        showNameCustomerLabel.setFont(new Font("宋体", Font.PLAIN, 30));
        showNameCustomerLabel.setBounds(348, 171, 144, 32);
        layeredPane.add(showNameCustomerLabel);

        showPasswordLabel = new JLabel("");
        showPasswordLabel.setFont(new Font("宋体", Font.PLAIN, 30));
        showPasswordLabel.setBounds(348, 218, 144, 32);
        layeredPane.add(showPasswordLabel);

        carScrollPane = new JScrollPane(carJtable);
        carScrollPane.setBounds(197, 271, 576, 249);
        layeredPane.add(carScrollPane);

        return_BookButton = new JButton(new ImageIcon("image/return.jpg"));
        return_BookButton.setBounds(401, 540, 123, 43);
        return_BookButton.setFont(new Font("宋体", Font.PLAIN, 30));
        return_BookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_return_car();
            }
        });
        layeredPane.add(return_BookButton);

        show_data();

        JLabel background1 = new JLabel(new ImageIcon("image/background2.jpg"));
        background1.setBounds(0, 0, 990, 659);
        contentPane.add(background1);
    }

    private void do_return_car() {

        row = carJtable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "请选择车辆", "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        BorrowTools borrowtools = new BorrowTools();
        String idbook = carJtable.getValueAt(row, 0).toString();
        int i = borrowtools.ReturnCar(idbook);
        if (i == 1) {
            JOptionPane.showMessageDialog(this, "还车成功", "", JOptionPane.WARNING_MESSAGE);
            show_data();
            return;
        } else {
            JOptionPane.showMessageDialog(this, "还车失败", "", JOptionPane.WARNING_MESSAGE);
            show_data();
            return;
        }
    }

    private void show_data() {

        carJtable = new JTable();
        carJtable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        carJtable.setRowHeight(54);

        defaultModel = (DefaultTableModel) carJtable.getModel();
        defaultModel.setRowCount(0);
        defaultModel.setColumnIdentifiers(new Object[] { "编号", "车名", "押金", "租金"});
        carJtable.getTableHeader().setReorderingAllowed(false);
        carJtable.setModel(defaultModel);

        carJtable.getColumnModel().getColumn(0).setPreferredWidth(10);
        carJtable.getColumnModel().getColumn(1).setPreferredWidth(80);
        carJtable.getColumnModel().getColumn(2).setPreferredWidth(20);
        carJtable.getColumnModel().getColumn(3).setPreferredWidth(20);

        CustomerTools customerTools = new CustomerTools();
        Customer customer = new Customer();
        BorrowTools borrowtools = new BorrowTools();

        if (showidCustomerLabel.getText() != null && !"".equals(showidCustomerLabel.getText())) {
            customer.setUserID(showidCustomerLabel.getText());
        } else {
            JOptionPane.showMessageDialog(this, "请输入客户编号", "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        java.util.List<Customer> customerList = customerTools.customersData(customer.getUserID());
        List<Car> carList = borrowtools.CarData(customer.getUserID());

        // Check the idReader

        if (customerList.size() == 0) {
            JOptionPane.showMessageDialog(this, "客户编号错误，请输入正确的客户编号 ", "", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            for (Iterator<Customer> iterator = customerList.iterator(); iterator.hasNext();) {
                Customer temp = (Customer) iterator.next();
                showNameCustomerLabel.setText(temp.getUsername());
                showPasswordLabel.setText(temp.getUserPassword());
            }
            for (Iterator<Car> iterator = carList.iterator(); iterator.hasNext();) {
                Car temp = (Car) iterator.next();
                defaultModel.addRow(new Object[] { temp.getCarID(), temp.getCarName(), temp.getCarPrice() + "元",
                        temp.getBorrowPrice() + "元" });
            }
        }
        carScrollPane.setViewportView(carJtable);
    }

    public void CloseFrame() {
        super.dispose();
    }
}
