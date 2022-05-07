package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraftFactory.BossEnemyFactory;
import edu.hitsz.aircraftFactory.EliteEnemyFactory;
import edu.hitsz.aircraftFactory.MobEnemyFactory;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.aircraftFactory.AbstractEnemyAircraftFactory;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.music.MusicThread;
import edu.hitsz.properties.AbstractProp;
import edu.hitsz.properties.PropBomb;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import static edu.hitsz.application.Main.obj;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    protected int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    protected final List<AbstractEnemyAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;

    /** 产生精英敌机概率 */
    protected double propOfGeneratingEliteAircraft;

    /**页面上的所有道具*/
    private final List<AbstractProp> props;

    /**抽象的敌机产生工厂*/
    protected AbstractEnemyAircraftFactory abstractEnemyAircraftFactory;

    /**界面中敌机数量最大值*/
    protected int enemyMaxNumber = 5;

    private boolean gameOverFlag = false;
    protected int score = 0;
    protected int time = 0;

    class Cycle{
        public int CycleDuration;
        public int CycleTime;
        public Cycle(int cycleDuration, int cycleTime) {
            CycleDuration = cycleDuration;
            CycleTime = cycleTime;
        }
        public void setCycleDuration(int t){
            CycleDuration = t;
        }
        public void decreaseCycleDuration(int t){
            CycleDuration -= t;
            if (CycleDuration < 300){
                CycleDuration = 300;
            }
        }
    }
    /**
     * 周期（ms)
     * 指示敌机子弹发射频率
     */
    protected Cycle enemyShootCycle = new Cycle(600, 0);

    /**
     * 周期（ms)
     * 指示英雄机子弹发射频率
     */
    protected Cycle heroShootCycle = new Cycle(500, 0);

    /**
     * 周期（ms)
     * 指示敌机产生频率
     */
    protected Cycle generateEnemyCycle = new Cycle(600, 0);

    /**
     * 周期（ms)
     * 指示难度提升频率
     * 这里的10000会在子类构造函数中重写
     */
    protected Cycle increaseDifficultyCycle = new Cycle(10000, 0);

    /**boss机出现过的总次数*/
    protected int numOfBoss = 0;

    /**boss机出现的分数周期*/
    protected int cycleScoreOfBoss = 300;

    /**下次出现boss机的分数*/
    protected int nextScoreOfBoss = cycleScoreOfBoss;

    /**是否开启音乐*/
    protected static boolean musicOn = false;

    /**普通敌机每次出现血量*/
    protected int mobHp = 30;

    /**精英敌机每次出现血量*/
    protected int eliteHp = 30;

    /**boss每次出现血量*/
    protected int bossHp = 500;

    /**普通敌机速度*/
    protected int speedyOfMob = 5;

    /**精英敌机速度*/
    protected int speedyOfElite = 5;

    /**boss敌机速度*/
    protected int speedyOfBoss = 0;

    /**在每个周期内提升敌机速度的概率*/
    protected double propOfIncreasingEnemySpeedy = 0.5;

    /**游戏难度*/
    private int difficulty;

    private MusicThread bgmThread;
    private MusicThread bossThread;
    private MusicThread bulletHitThread;
    private MusicThread getSupplyThread;
    private MusicThread bombExplosionThread;
    private MusicThread gameOverThread;

    /**当前运行的音乐线程*/
    private List<MusicThread> musicThreadRunningList;

    /**判断boss是否活着*/
    protected boolean bossExistFlag;

    public Game(boolean musicOn) {
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        musicThreadRunningList = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        Game.musicOn = musicOn;

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        if (musicOn){
            bgmThread = getBgmThread();
            bgmThread.start();
        }
        Runnable task = () -> {

            time += timeInterval;
           // System.out.println("time:" + time);

            // 周期性执行敌机射击（控制频率）
            if (timeCountAndNewCycleJudge(enemyShootCycle)) {
                enemyShootAction();
            }

            // 周期性执行英雄机射击（控制频率）
            if (timeCountAndNewCycleJudge(heroShootCycle)){
                heroShootAction();
            }

            // 周期性执行敌机产生（控制频率）
            if (timeCountAndNewCycleJudge(generateEnemyCycle)){
                generateEnemyAircraft();
            }

            // 周期性提升难度
            if (timeCountAndNewCycleJudge(increaseDifficultyCycle)){
                if (increaseDifficultyFlag()){
                    increaseDifficulty();
                }
            }

            // 子弹移动
            bulletsMoveAction();

            //道具移动
            propsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            //检查音乐
            if (musicOn) {
                checkMusic();
            }

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                if(musicOn){
                    startGameOverMusicThread();
                }

                System.out.println("Game Over!");
                setVisible(false);
                synchronized(obj) {
                    obj.notifyAll();
                }
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }



    /**播放短音频：子弹射击、道具获取、游戏结束*/
    private void shortMisicThreadStart(MusicThread thread) {
        // 停止当前播放的其他音频
        getmusicThreadRunningList();
        for (MusicThread t:musicThreadRunningList){
            // 暂停背景音乐和boss音乐， 关闭其他音乐
            if(t.getFilename().contains("bgm")){
                t.setPause();
            }else{
                t.setInterrupt();
            }
        }
        // 开启自己的线程
        thread.start();
    }

    /**判断线程是否存在且正在运行*/
    private boolean existAndRunning(MusicThread t){
        return t!=null && t.isAlive();
    }

    /**从所有音乐线程中找到正在运行的*/
    private void getmusicThreadRunningList() {
        musicThreadRunningList.clear();
        if (existAndRunning(bgmThread)){
            musicThreadRunningList.add(bgmThread);
        }if(existAndRunning(bossThread)){
            musicThreadRunningList.add(bossThread);
        }if(existAndRunning(bulletHitThread)){
            musicThreadRunningList.add(bulletHitThread);
        }if(existAndRunning(bombExplosionThread)){
            musicThreadRunningList.add(bombExplosionThread);
        }if(existAndRunning(getSupplyThread)){
            musicThreadRunningList.add(getSupplyThread);
        }
    }

    private void checkMusic() {
        // 存在boss
        if (bossExistFlag){
            // 循环boss音乐
            if (bossThread!=null && !bossThread.isAlive()){
                bossThread = getBossThread();
                bossThread.start();
            }
        }
        // boss不在
        else{
            // 关闭boss音乐
            if(bossThread!=null && bossThread.isAlive()){
                bossThread.setInterrupt();
            }
            // 打开背景音乐
            if(bgmThread!=null && !bgmThread.isAlive()){
                bgmThread = getBgmThread();
                bgmThread.start();
            }
        }

    }

    //***********************
    //      Action 各部分
    //***********************

    // 增加当前时间，判断是否跨越周期
    protected final boolean timeCountAndNewCycleJudge(Cycle cycle) {
        cycle.CycleTime += timeInterval;
        if (cycle.CycleTime >= cycle.CycleDuration && cycle.CycleTime - timeInterval < cycle.CycleDuration) {
            // 跨越到新的周期
            cycle.CycleTime %= cycle.CycleDuration;
            return true;
        } else {
            return false;
        }
    }

    // 产生敌机
    protected abstract void generateEnemyAircraft();

    // 产生精英敌机和普通敌机
    protected final void generateMobAndEliteAircraft(){
        if (enemyAircrafts.size() < enemyMaxNumber) {
            //以0.2概率产生精英敌机，0.8概率产生普通敌机
            if (Math.random() >= propOfGeneratingEliteAircraft) {
                abstractEnemyAircraftFactory = new MobEnemyFactory();
                enemyAircrafts.add(abstractEnemyAircraftFactory.createEnemyAircraft(mobHp, speedyOfMob));
            } else {
                abstractEnemyAircraftFactory = new EliteEnemyFactory();
                enemyAircrafts.add(abstractEnemyAircraftFactory.createEnemyAircraft(eliteHp, speedyOfElite));
            }
        }
    }

    // 产生boss敌机
    protected final void generateBossAircraft(){
        //分数达到设定阈值的倍数，出现boss敌机
        if (score > nextScoreOfBoss && score / cycleScoreOfBoss > numOfBoss && !bossExistFlag) {
            bossExistFlag = true;
            abstractEnemyAircraftFactory = new BossEnemyFactory();
            enemyAircrafts.add(abstractEnemyAircraftFactory.createEnemyAircraft(bossHp, speedyOfBoss));
            numOfBoss++;
            nextScoreOfBoss += cycleScoreOfBoss;

            if(musicOn){
                bossThread = getBossThread();
                bossThread.start();
            }
            System.out.printf("boss敌机血量:%d\n",bossHp);
        }
    }

    // 是否随时间提升难度
    public boolean increaseDifficultyFlag(){
        return true;
    }

    // 随时间提升难度
    public abstract void increaseDifficulty();

    protected final void heroShootAction() {
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    protected final void enemyShootAction(){
        // TODO 敌机射击
        for (AbstractAircraft eliteaircraft :enemyAircrafts) {
            enemyBullets.addAll(eliteaircraft.shoot());
        }
    }

    protected final void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    protected final void propsMoveAction() {
        for (AbstractProp prop : props){
            prop.forward();
        }
    }

    protected final void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    protected final void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)){
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    if(musicOn){
                        // 子弹击中音乐
                        setBulletHitThreadAndStart();
                    }
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if (enemyAircraft instanceof MobEnemy){
                            //消灭普通敌机，+10分
                            destroyMobEnemyAndAddScore();
                        }
                        else if (enemyAircraft instanceof EliteEnemy){
                            //消灭英雄敌机，+20分
                            destroyEliteEnemyAndAddScore();
                            AbstractProp prop = enemyAircraft.generateProp();
                            if (prop != null){
                                props.add(prop);
                            }
                        }
                        else if (enemyAircraft instanceof BossEnemy) {
                            //消灭boss敌机，+50分
                            destroyBossEnemyAndAddScore();
                            bossExistFlag = false;
                            if(musicOn){
                                // 开启boss音乐
                                bossThread.setInterrupt();
                            }
                            AbstractProp prop = enemyAircraft.generateProp();
                            if (prop != null){
                                props.add(prop);
                            }
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : props){
            if (prop.notValid()) {
                continue;
            }
            // 我方获取道具
            if (heroAircraft.crash(prop)){
                // 记录获得时间
                prop.setStartTime(time);
                // 开启音乐
                if(musicOn){
                    if (prop instanceof PropBomb){
                        // 炸弹音效
                        setBombThreadAndStart();
                    }else {
                        // 获得道具音效
                        setGetSupplyThreadAndStart();
                    }
                }
                // 道具生效
                if (prop instanceof PropBomb){
                    // 炸弹道具
                    // 注册所有观察者  并  获得分数
                    for (AbstractEnemyAircraft aircraft : enemyAircrafts){
                        if (aircraft instanceof EliteEnemy){
                            ((PropBomb) prop).addSubscriber((EliteEnemy) aircraft);
                            destroyEliteEnemyAndAddScore();
                        }else if(aircraft instanceof MobEnemy){
                            ((PropBomb) prop).addSubscriber((MobEnemy) aircraft);
                            destroyMobEnemyAndAddScore();
                        }
                    }
                    for (BaseBullet bullet:enemyBullets){
                        ((PropBomb) prop).addSubscriber((EnemyBullet) bullet);
                    }

                    // 道具生效，给观察者发送消息
                    prop.operate();
                }else {
                    // 火力道具和加血道具
                    prop.operate();
                }
                // 道具消失
                prop.vanish();
            }
        }
    }

    /**消灭普通敌机，加10分*/
    protected final void destroyMobEnemyAndAddScore() {
        score += 10;
    }

    /**消灭精英敌机，加20分*/
    protected final void destroyEliteEnemyAndAddScore() {
        score += 20;
    }

    /**消灭boss敌机，加50分*/
    protected final void destroyBossEnemyAndAddScore() {
        score += 50;
    }

    /**创建子弹射击音频线程并启动*/
    protected final void setBulletHitThreadAndStart() {
        bulletHitThread = new MusicThread("src/videos/bullet_hit.wav",0);
       // System.out.println("new hit music");
        shortMisicThreadStart(bulletHitThread);
    }

    /**创建获得道具音频线程并启动*/
    protected final void setGetSupplyThreadAndStart(){
        getSupplyThread = new MusicThread("src/videos/get_supply.wav",0);
       // System.out.println("new supply music");
        shortMisicThreadStart(getSupplyThread);
    }

    /**创建获得炸弹音频线程并启动*/
    protected final void setBombThreadAndStart(){
        bombExplosionThread = new MusicThread("src/videos/bomb_explosion.wav",0);
       // System.out.println("new bomb music");
        shortMisicThreadStart(bombExplosionThread);
    }

    /**创建游戏结束音频线程并启动*/
    protected final void startGameOverMusicThread() {
        gameOverThread = new MusicThread("src/videos/game_over.wav",0);
        getmusicThreadRunningList();
        for (MusicThread t:musicThreadRunningList){
            // 关闭所有音乐
            t.setInterrupt();
        }
        gameOverThread.start();
    }

    /**获取bgm实例*/
    protected final MusicThread getBgmThread() {
        return new MusicThread("src/videos/bgm.wav",1);
    }

    /**获取boss bgm实例*/
    protected final MusicThread getBossThread() {
        return new MusicThread("src/videos/bgm_boss.wav",1);
    }

    /**获取当前时间*/
    protected final String getCurrentTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除超过时效的道具
     * 4. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */

    protected final void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
       // removeTimeExceededProps();
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public final void paint(Graphics g) {
        super.paint(g);

        // 调用子类函数，绘制背景图片
        drawBackground(g);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，再绘制道具，最后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, props);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    /**绘制背景图片，放在子类完成*/
    public abstract void drawBackground(Graphics g);

    protected final void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    protected final void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    protected final void setDifficulty(int x){
        difficulty = x;
    }

    protected final int getDifficulty(){
        return difficulty;
    }

    protected final int getScore() {
        return score;
    }
}
