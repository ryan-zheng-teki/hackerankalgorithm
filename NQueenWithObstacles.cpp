#include <cstdio>
#include <string>
#include <cstring>

using namespace std;

const int M=1000000007;

char s[10];
int dp[2][32][32][32]; //left,right,down
int n,r,x,y,z;

inline int give(int x,int y) {
	return (x>>y)&1;
}

void gao(bool can,int now,int state,int temp) {
int a,b,c;
	if (now==n) {
		a=((x>>1)|temp)&state;
		b=((y<<1)|temp)&state;
		c=(z|temp)&state;
		if ((dp[r][a][b][c]+=dp[r^1][x][y][z])>=M) {
			dp[r][a][b][c]-=M;
		}
		return;
	}
	state|=((s[now]=='.')?1:0)<<now;
	if (can && (s[now]=='.') && (!give(x>>1,now)) && (!give(y<<1,now)) && (!give(z,now))) {
		gao(false,now+1,state,temp|(1<<now));
	}
	gao(can || (s[now]=='#'),now+1,state,temp);
}

int main() {
int mm,m,i,t;
	for (scanf("%d",&t);t;--t) {
		scanf("%d%d",&m,&n);
		mm=(1<<n);
		memset(dp[0],0,sizeof(dp[0]));
		dp[0][0][0][0]=1;
		for (i=1,r=0;i<=m;++i) {
			memset(dp[r=i&1],0,sizeof(dp[0]));
			scanf("%s",s);
			for (x=0;x<mm;++x) {
				for (y=0;y<mm;++y) {
					for(z=0;z<mm;++z) {
						if (dp[r^1][x][y][z]) {
							gao(true,0,0,0);
						}
					}
				}
			}
		}
		for (x=i=0;x<mm;++x) {
			for (y=0;y<mm;++y) {
				for (z=0;z<mm;++z) {
					if ((i+=dp[r][x][y][z])>=M) {
						i-=M;
					}
				}
			}
		}
		if (--i<0) {
			i+=M;
		}
		printf("%d\n",i);
	}
	return 0;
}
