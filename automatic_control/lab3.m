%% LAB 3
% Liisa Huss, Therese Fries

clear all;
close all;
clc;

%% setup 

%Get J and umax
[J, umax] = lab3robot(020706);
%J=5;
%umax=90;

%constants 
Lm = 2;
Rm = 21;
b = 1;
Kt = 38;
Km = 0.5;
n = 1/20;

%% Assignment 1

% create system
s = tf('s');
G = (n*Kt)/(s*((s*Lm+Rm)*(J*s+b)+Kt*Km)); %correct

% print out with numerical values
disp('Assignment 1: Transfer function G(s):');
G

lab3robot(G,020706);

%% Assignment 2, P-controller
% M<5%, shortest Tr as possible
% Higher K - shorter Tr but higher overshoot

K = 4.12; % Higher K -> too high overshoot 

% Closed loop Gc = KG/(1+KG)
Gc = feedback(K*G, 1);

info = stepinfo(Gc);

fprintf('Overshoot: %.2f %%\n', info.Overshoot);
fprintf('Rise time: %.3f s\n', info.RiseTime);

figure(1);
step(Gc);
title(sprintf('Step response P-controller, K = %.2f', K));
grid on;

rlocus(K*G);
% M=4.99%, Tr=7.619s

%lab3robot(G, K, 1, 1, 1, 1, 1, 1, 020706); %correct
%% Assignment 3

%open and closed systems
K = 4.12;
Go = K * G;

% Gc = Go/(1+Go)
Gc = feedback(Go, 1);

[~, Pm, ~, Wcp] = margin(Go);
Bw = bandwidth(Gc);

%Gm = gain margin
%Pm = phase margin
%Wcg = gain crossover frequency
%Wcp = phase crossover frequency

disp(['Phase margin: ', num2str(Pm), ' deg']);
disp(['crossover frequecy: ', num2str(Wcp), ' rad/s']);
fprintf('Bandwidth : %.2f \n', Bw);

figure(1);
margin(Go);
title('Bode diagram open-loop');

figure(2);
margin(Gc);
title('Bode diagram closed-loop');

%for assignment 6, new wcp
omega_new = 4*Wcp;

[~, phase] = bode(Go, omega_new);

new_phase = phase; 
fprintf('Ny önskad skärfrekvens: %.4f rad/s\n', omega_new);
fprintf('Systemets fas vid denna frekvens: %.2f grader\n', new_phase);

% the new phase margin: 180-155.35=24.65 -> have to add 39.73 to keep the 
% phase margin at 64.38

%% Assignment 4.1

hold on
legends = {};

K_values=[1, 2, 4, 6, 8, 10, 12];
for n = 1:length(K_values)
    K = K_values(n);
    F=K;
    Go = F * G;
    bode(Go)

    legends{end+1} = ['K = ', num2str(K)];
    [GM, PM, Wcg, Wcp] = margin(Go);

    disp(K)
    disp(['Phase margin: ', num2str(PM), ' deg']);
    disp(['Gain margin: ', num2str(GM), ' deg']);
    disp(['Phase Crossover frequency: ', num2str(Wcp), ' rad/s']);
    disp(['Gain Crossover frequency: ', num2str(Wcg), ' rad/s']);


end

legend(legends); 
grid on;
hold off

%% Assignment 4.2/4.3/4.4

% Analyze system G when K=1

% K = 1; 
% F = K;
% Go=F*G;
% 
% % Wcp is the frquency where the phase is -180 deg.
% [Gm, Pm, Wcg, Wcp] = margin(Go);
% 
% K_instabil = Gm;      % K that makes the system unstable
% Frekvens_instabil = Wcp;     % frequency where is happens (Wcp)

% happens when K = 225.2632

K = 225; 
F = K;

% Closed loop Gc = FG/(1+FG)
Gc = feedback(F*G, 1);

% Step response for very high K but still stable
figure;
step(Gc, 30000);
legend(['K = ', num2str(K)]);

% step response for different K 
figure(2); 
clf;
hold on
legends = {};
for K=1:2:20;
     F = K;
     Go = F*G ;

     Gc = feedback(F*G, 1);

     step(Gc, 100); grid

     legends{end+1} = ['K = ', num2str(K)];

end
legend(legends); 
hold off

% step response, unstable when K=225.2632
figure(3);
clf;
hold on
legends = {};
K=225.2632;
F=K;
Gc=feedback(F*G, 1);
step(Gc, 100000000); grid
legends{end+1} = ['K = 225.2632'];
legend(legends);
hold off

% Bode for unstable system
figure(4);
clf;
hold on

K=225.2632;
F=K;
Go=F*G;

margin(Go);

[GM, PM, Wcg, Wcp] = margin(Go);
disp(['Phase margin: ', num2str(PM), ' grader']);

hold off

%% Assignment 6

% create system 
s = tf('s');

% beta and tau_d for the phase lift to keep phase margin

wc_new = 0.7116; % new cross-over freq.
beta = 0.16; % value taken from course literature 

tau_d = 1 / (wc_new * sqrt(beta));

% K so we have the right cross-over freq.
% K = sqrt(beta)/|G(iwc)|
[mag] = bode(G, wc_new);
K = sqrt(beta) / mag; 

% Stationary control error < 0.05 
e1= 40 /(1.9 * K);
factor = e1 / 0.05;

tau_i = 20/wc_new;
gamma = 1 / factor;

% Transfer function
F_lead_lag = K * (tau_d * s + 1)*(tau_i * s + 1) / ((beta * tau_d * s  +1) ...
             * (tau_i * s + gamma));

% open loop 
Go_lead_lag = F_lead_lag * G;

% Closed loop Gc = FG/(1+FG)
Gc = feedback(F_lead_lag*G, 1);

% check u<umax, Gu = F / (1 + F*G)
Gu_lead_lag = feedback(F_lead_lag, G);

% Plots
% Plot Bode for open loop
figure(1); 
margin(Go_lead_lag); 
grid on;
title('Bode-diagram (Lead-Lag)');

% Plot closed loop (y)
figure(2); 
step(Gc, 100); 
grid on;
title('Step response');

% Plot control signal (u)
figure(3); 
step(Gu_lead_lag); 
grid on;
title('Control signal step response (u)');

% Plot Bode for control signal (u)
figure (4);
margin(Gu_lead_lag);
grid on;
title('Bode-diagram (control signal)');

info1 = stepinfo(Gc);
fprintf('Overshoot: %.2f%%\n', info1.Overshoot);
fprintf('Rise time: %.3f s\n', info1.RiseTime);

% print out with numerical values
disp('Assignment 6: transfer funtion for lead-lag:');
F_lead_lag
 
%lab3robot(G,4.12,F_lead_lag,1,1,1,1,1,020706)

%% Assignment 8

F_prop = 4.12; % K-value for proportional controller

% Sensitivity function 1/(1+FG)=1/(1+Go)
S_prop = feedback(1, F_prop*G);
S_lead_lag = feedback(1, Go_lead_lag);

bodemag(S_prop, S_lead_lag); grid
legend('S(s) Proportional', 'S(s) Lead-Lag');

%% Assignment 9

T_lead_lag = 1 - S_lead_lag; 

delta_G1 = (s + 10) / 40;
delta_G2 = (s + 10) / (4 * (s + 0.01));

real_G1 = G * (1 + delta_G1);
real_G2 = G * (1 + delta_G2);

bodemag(T_lead_lag, 1/delta_G1, 1/delta_G2); grid
legend('T(s)', '1/\DeltaG1', '1/\DeltaG2')

%% Assignment 11

%Get J and umax
PersonalNumber = 020706;
[J, umax] = lab3robot(PersonalNumber);
%J=5;
%umax=90;

%constants 
Lm = 2;
Rm = 21;
b = 1;
Kt = 38;
Km = 0.5;
n = 1/20;

% calculated matrices

A = [0, n, 0; 0, -b/J, Kt/J; 0, -Km/Lm, -Rm/Lm];
B = [0; 0; 1/Lm];
C = [1, 0, 0];

% Controllability 
S_matrix = ctrb(A, B);

% Observability
O_matrix = obsv(A, C);

rank_S = rank(ctrb(A, B));
rank_O = rank(obsv(A, C));

fprintf('Rank S:   %d\n', rank_S);
fprintf('Rank O: %d\n', rank_O);

%lab3robot(G,4.12,F_lead_lag,A,B,C,1,1,020706);

%% Assignment 12

s = tf('s');

wc_new = 0.7116; % new cross-over freq.
beta = 0.16; % value taken from course literature 

tau_d = 1 / (wc_new * sqrt(beta));

[mag] = bode(G, wc_new);
K = sqrt(beta) / mag; %s.110 mag*K*1/sqrt(beta)

e1= 40 /(1.9 * K);
factor = e1 / 0.05;

tau_i = 20/wc_new;
gamma = 1 / factor;

F_lead_lag = K * (tau_d * s + 1)*(tau_i * s + 1) / ((beta * tau_d * s  +1) ...
             * (tau_i * s + gamma));


A = [0, n, 0; 0, -b/J, Kt/J; 0, -Km/Lm, -Rm/Lm];
B = [0; 0; 1/Lm];
C = [1, 0, 0];

% Parameters for poles
wn = 2.26;     % further away gave too large control signal
zeta = 0.707;  % < 5% overshoot (45 deg.)
alpha = 2.3;   % Further away gave too large control signal

s1 = -alpha;
s2 = -wn*zeta + 1i*wn*sqrt(1-zeta^2);
s3 = -wn*zeta - 1i*wn*sqrt(1-zeta^2);

poles = [s1, s2, s3];

% Compute L such that the closed-loop poles (eigenvalues of A-B*L) 
% match the desired 'poles'.
L = place(A, B, poles);

% calculated l0
l0 = 1 / ( -C * inv(A - B*L) * B );

% Gc = C(sI - A + BL)^-1*Bl0
Gc = C * (s * eye(3) - A + B * L)^(-1) * B * l0;

info = stepinfo(Gc);

fprintf('Overshoot: %.1f%%  |  RiseTime: %.3fs\n', info.Overshoot, info.RiseTime);

%print out L and l0 numerical values
disp('--- Control Law ---');
fprintf('L  = [%.4f  %.4f  %.4f]\n', L);
fprintf('l0 = %.4f\n', l0);

% step response Gc
figure (1);
step(Gc);
grid on;
title('Step response Gc');

% Bode Gc
figure (2);
bode(Gc);
grid on;
title('Bode diagram Gc');

% Root locus
figure (3);
rlocus(Gc);
title('Root locus Gc');

% CHECK control signal

% system where u is outsignal (u = -Lx + l0*r)
% Kommandot ss (State Space) tar in fyra matriser: A, B, C, D.
% u = -Lx + l_o*r = Cx + Dr | C = -L, D = l0

sys_u = ss(A - B*L, B*l0, -L, l0);

% Plot stepresponse (< 90)
figure (4); 
step(sys_u); grid on;
title('Control signal u(t)');

% Plot Bode 
figure (5); 
bode(sys_u); grid on;
title('Bode diagram u(t)');

%%
lab3robot(G,4.12,F_lead_lag,A,B,C,L,l0,020706);


