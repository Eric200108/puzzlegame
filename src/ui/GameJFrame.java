package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    int[][] data = new int[4][4];
    int x = 0;
    int y = 0;

    String path = "image\\animal\\animal8\\";

    //胜利时的数组坐标
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    //步数
    int count = 0;

    //创建选项
    JMenuItem replayItem = new JMenuItem("重新开始");
    //JMenuItem reloginItem = new JMenuItem("重新登录");
    JMenuItem exitItem = new JMenuItem("关闭游戏");

    JMenuItem girl = new JMenuItem("女孩");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");

    public GameJFrame() {
        //初始化窗口
        initJFrame();

        //初始化菜单栏
        initJMenuBar();

        //初始化数据
        initData();

        //初始化图片
        initImage();

        //是否可见
        this.setVisible(true);
    }

    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        for (int i = 0; i < tempArr.length; i++) {
            Random r = new Random();
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                data[i][j] = tempArr[i * 4 + j];
                if (data[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }
    }

    private void initImage() {

        this.getContentPane().removeAll();

        if (victory()) {
            ImageIcon win = new ImageIcon("image\\win.png");
            JLabel jLabel = new JLabel(win);
            jLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(jLabel);
        }

        //显示步数
        JLabel step = new JLabel("步数：" + count);
        step.setBounds(50,30,100,20);
        this.getContentPane().add(step);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //创建imageicon对象
                int num = data[i][j];
                ImageIcon imageIcon = new ImageIcon(path + num + ".jpg");
                //把imageicon放入jlable
                JLabel jLabel = new JLabel(imageIcon);
                //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //给图片添加边框
                jLabel.setBorder(new BevelBorder(1));
                //将jlable添加到界面
                this.getContentPane().add(jLabel);
            }
        }

        //添加背景
        ImageIcon bg = new ImageIcon("image\\background.png");
        JLabel background = new JLabel(bg);
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);

        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        //创建菜单栏对象
        JMenuBar jMenuBar = new JMenuBar();
        //创建菜单对象
        JMenu functionJMenu = new JMenu("功能");
        JMenu changePictureJMenu = new JMenu("更换图片");

        //将选项添加到菜单中
        functionJMenu.add(changePictureJMenu);
        functionJMenu.add(replayItem);
        //functionJMenu.add(reloginItem);
        functionJMenu.add(exitItem);
        changePictureJMenu.add(girl);
        changePictureJMenu.add(animal);
        changePictureJMenu.add(sport);

        //选项绑定事件
        replayItem.addActionListener(this);
        //reloginItem.addActionListener(this);
        exitItem.addActionListener(this);
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);

        //将菜单添加到菜单栏中
        jMenuBar.add(functionJMenu);
        //添加菜单栏
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        //窗口的宽高
        this.setSize(603, 680);
        //界面标题
        this.setTitle("拼图游戏");
        //界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //取消默认布局
        this.setLayout(null);
        //添加键盘监听
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (victory()) {
            return;
        }
        int code = e.getKeyCode();
        if (code == 65) {
            this.getContentPane().removeAll();

            //显示完整图片
            ImageIcon all = new ImageIcon(path + "all.jpg");
            JLabel jLabel = new JLabel(all);
            jLabel.setBounds(83, 134, 420, 420);
            this.getContentPane().add(jLabel);
            //添加背景
            ImageIcon bg = new ImageIcon("image\\background.png");
            JLabel background = new JLabel(bg);
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);

            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (victory()) {
            return;
        }
        //左：37 上：38 右：39 下：40
        int code = e.getKeyCode();
        if (code == 37) {
            //左
            if (y == 3) {
                return;
            }
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            //步数加一
            count++;
            initImage();
        } else if (code == 38) {
            //上
            if (x == 3) {
                return;
            }
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            //步数加一
            count++;
            initImage();
        } else if (code == 39) {
            //右
            if (y == 0) {
                return;
            }
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            //步数加一
            count++;
            initImage();
        } else if (code == 40) {
            //下
            if (x == 0) {
                return;
            }
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            //步数加一
            count++;
            initImage();
        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            initImage();
        }
    }

    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == replayItem){
            //重新开始
            count = 0;
            initData();
            initImage();
        /*} else if (obj == reloginItem) {
            //重新登录
            this.setVisible(false);
            new LoginJFrame();*/
        } else if (obj == exitItem) {
            System.exit(0);
        } else if (obj == girl) {
            Random r = new Random();
            int num = r.nextInt(11) + 1;
            //更改路径
            path = "image\\girl\\girl"+ num +"\\";
            //重新计步
            count = 0;
            //加载
            initData();
            initImage();
        } else if (obj == animal) {
            Random r = new Random();
            int num = r.nextInt(8) + 1;
            //更改路径
            path = "image\\animal\\animal"+ num +"\\";
            //重新计步
            count = 0;
            //加载
            initData();
            initImage();
        } else if (obj == sport) {
            Random r = new Random();
            int num = r.nextInt(10) + 1;
            //更改路径
            path = "image\\sport\\sport"+ num +"\\";
            //重新计步
            count = 0;
            //加载
            initData();
            initImage();
        }
    }
}
