A = load('check.csv');

x = A(:,1);
y1 = A(:,2);
y2 = A(:,3);
y3 = A(:,4);
y4 = A(:,5);
y5 = A(:,6);
y6 = A(:,7);

hold on;
plot(x,y1,'b-');
plot(x,y2,'b--');
plot(x,y3,'b-.');
plot(x,y4,'r-');
plot(x,y6,'m-');
plot(x,y5,'g-');
axis([0 3 -2.5 2.5]);
legend('Square 50%','Square 25%','Square 12.5%','Sawtooth','Sawtooth 5HZ','Triangle');
xlabel('Time (seconds)');
ylabel('Amplitude');
title('CS259 Inhertiance Example With Waveforms');
hold off;

saveas(gca,'myform.eps','epsc');