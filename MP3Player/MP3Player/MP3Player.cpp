#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h> 
#include <io.h>
#include <ctype.h>
#include <time.h>

#pragma comment(lib, "WINMM.LIB")

#define N 100
#define M 20
typedef struct
{
	char title[50];
	long length;
	char path[100];
}Music;

char mciStr[N] = "";		//ȫ�ֱ��������ڼ�¼mciSendString������
char str[M] = "";
char filepath[50]="";
unsigned int volume = 500;	//����
int currentListNumber = 1;	//��ǰ�����б���
int judgement = 1;
int p = 0;					//��ǰ�����б������Ŀ
int *H = &p;
int playMode = 1;			//��ǰ����ģʽ
int playStatu = 1;	//����״̬,1Ϊ�������ţ�0Ϊֹͣ����
FILE *fp;
Music *musicList;			//��ǰ�����б�
Music currentMusic;			//��ǰ���Ÿ���
HANDLE PLAYMENU;
//HANDLE checkStop;

void menu();
void DisplayList(int judge);
Music SearchMusic();
void DelMusic();
void ModifyList();
void ChangeList();
Music* SearchLocalMusic(Music *musicList, int *H);
void ChangePlayMode();
int PlayMusic(char* path);
int PlayMenu();
void PlaseMusic();
void ContinueMusic();
void SpeedMusic();
void BackwardMusic();
void UpVolume();
void DownVolume();
void SoundOff();
void SoundOn();
Music* ChangeMusic(Music *musicList, Music currentMusic, int musicQuantity, int norl);
void DisplayMusicInfo(Music music);
void LoadMusicList(int listNumber);
void SaveMusicList(int listNumber);
void Play();
void checkStopFun();
DWORD WINAPI PlayMenuThread(LPVOID lParam);

/*
�ú���Ϊ��������

author:����2015141463132
version:1.1
v1.1:���������������Ѻ���ʾ
*/
void main()
{
	int choice;
	system("color D"); 
	printf("\n\n\n");
	printf("\t\t\t\t****************************\n");
	printf("\t\t\t\t*��ӭʹ��MP3���ֲ�����!    *\n");
	printf("\t\t\t\t*�汾��Ϣ��V1.0\t\t   *\n\t\t\t\t*���ߣ��������캬����Զ��*\n");
	printf("\t\t\t\t****************************\n\n\n\n");
	LoadMusicList(currentListNumber);
	printf("������1�������˵�,����0�˳�������\n");
	scanf("%d", &choice);
	if (choice == 0)
	{
		return;
	}
	else if (choice!=0&&choice!=1)
	{
		printf("�������ϵͳ�Զ��˳�\n");
		return;
	}
	else
	{
		while (1)
		{
			menu();
			printf("��ѡ��������Ҫ�Ĺ���:\n");
			scanf("%d", &choice);
			getchar();
			switch (choice)
			{
			case 1:
				DisplayList(currentListNumber);
				break;
			case 2:
				SearchMusic();
				break;
			case 3:
				DelMusic();
				break;
			case 4:
				ModifyList();
				break;
			case 5:
				ChangeList();
				break;
			case 6:
				musicList = SearchLocalMusic(musicList, H);
				SaveMusicList(currentListNumber);
				LoadMusicList(currentListNumber);
				break;
			case 7:
				ChangePlayMode();
				break;
			case 8:
				Play();
				break;
			case 0:
				exit(0);
			default:
				break;
			}
		}
	}

	free(musicList);
	musicList = NULL;

	return;
}

/*
�ú���������ʾ��������˵���

author:����2015141463132
version:1.1:��ӡ�����б��Ϊһ��2����ʹ�����ࡣ
v1.1:
*/
void menu()
{
	printf("\n");
	printf("\t1.�鿴�����б�\t	2.���Ҹ���\n\n");
	printf("\t3.ɾ������\t	4.�޸ĸ����б�\n\n");
	printf("\t5.�л������б�\t	6.�������ظ���\n\n");
	printf("\t7.�л�����ģʽ\t	8.���Ÿ���\n\n");
	printf("\t0.�˳�������\n\n");

	return;
}//��ʾ���˵�

 /*
 �ú���������ʾ���Ų˵����������û���������к�������ת��

 author:����2015141463132
 version:1.2
 v1.1:�����˷������˵�ʱ�Ĺر����ֲ��Ź���
 v1.2:�����˸����л�ʱ�Ĺر����ֲ��Ź���
 */
int PlayMenu()
{
	int choice;
	printf("��ʼ���Ÿ���:%s\n\n",currentMusic.title);
	printf("��ѡ���ܣ�\n\n");
	printf("\t1.��ͣ\t\t\t\t2.����\n\n");
	printf("\t3.���\t\t\t\t4.����\n\n");
	printf("\t5.��һ��\t\t\t6.��һ��\n\n");
	printf("\t7.��������\t\t\t8.��С����\n\n");
	printf("\t9.�ر�����\t\t\t10.�ָ�����\n\n");
	printf("\t11.�鿴������Ϣ\t\t\t0.�������˵�\n\n");
	scanf("%d", &choice);
	switch (choice)
	{
	case 1:
		PlaseMusic();
		break;
	case 2:
		ContinueMusic();
		break;
	case 3:
		SpeedMusic();
		break;
	case 4:
		BackwardMusic();
		break;
	case 5:
		currentMusic = *ChangeMusic(musicList, currentMusic, *H, 0);
		strcpy(mciStr, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		playStatu = 2;
		return 2;
		break;
	case 6:
		currentMusic = *ChangeMusic(musicList, currentMusic, *H, 1);
		strcpy(mciStr, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		playStatu = 2;
		return 2;
		break;
	case 7:
		UpVolume();
		break;
	case 8:
		DownVolume();
		break;
	case 9:
		SoundOff();
		break;
	case 10:
		SoundOn();
		break;
	case 11:
		DisplayMusicInfo(currentMusic);
		break;
	case 0:
		strcpy(mciStr, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		playStatu = 2;
		return 0;
	default:
		break;
	}

	playStatu = 1;
	return 1;
}

 /*
 �ú��������û��Բ��Ÿ���������ת�����Ų˵�

 author:����2015141463132
 version:1.0
 author:�캬
 v1.1:2016/7/28 ��Ӽ��������Ž����߳�
 */
int PlayMusic(char* path)
{
	char mciStr[100];
	char str[20];
	//Music pMusic;
	printf("\n\n");
	//printf("������Ҫ���ŵĸ�����·����\n");
	//scanf("%s",&pMusic.path);
	strcpy(mciStr, "open ");
	strcat(mciStr, path);
	strcat(mciStr, " alias mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	if (playMode == 2)
	{
		mciSendStringA("play movie repeat", 0, 0, 0);
	}
	//printf("Open\n");
	strcpy(mciStr, "play mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	//printf("Play\n");
	strcpy(mciStr, "setaudio mp3 volume to ");
	strcat(mciStr, _itoa(volume,str,10));
	mciSendStringA(mciStr, 0, 0, 0);

	int playStatu = 1;
	while (playStatu == 1)
	{
		playStatu = PlayMenu();
	}
	
	return playStatu;
}

/*
�ú�����ͣ��ǰ���ŵĸ���

author:����2015141463132
version:1.0
*/
void PlaseMusic()
{
	strcpy(mciStr, "pause mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Pause\n");
}
	
/*
�ú���������ǰ���ŵĸ���

author:����2015141463132
version:1.0
*/
void ContinueMusic()
{
	strcpy(mciStr, "resume mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Resume\n");
}

/*
�ú��������ǰ���ŵĸ���10s

author:����2015141463132
version:1.1
v1.1:����ӡ�����Ϊ�ֺ�����ʾ
*/
void SpeedMusic()
{
	int position = 0;
	int step = 10000;
	strcpy(mciStr, "status mp3 position");
	mciSendStringA(mciStr, str, M, 0);
	position = strtol(str, NULL, 10);
	printf("position = %d:%d\n", position/1000/60, (position/1000)%60);
	memset(str, 0, sizeof(str));
	position = position + step;
	//strcpy(mciStr, "play mp3 from ");
	//itoa(position, str, 10);
	//strcat(mciStr, str);
	//mciSendString(mciStr, 0, 0, 0);
	//printf("Go forward 10s\n");

	_itoa(position, str, 10);
	sprintf(mciStr, "seek mp3 to %s", str);
	mciSendStringA(mciStr, 0, 0, 0);
	mciSendStringA("play mp3", 0, 0, 0);
	printf("Go forward 10s\n");
}

/*
�ú������˵�ǰ���ŵĸ���10s

author:����2015141463132
version:1.1
v1.1:����ӡ�����Ϊ�ֺ�����ʾ
*/
void BackwardMusic()
{
	int position = 0;
	int step = 10000;
	strcpy(mciStr, "status mp3 position");
	mciSendStringA(mciStr, str, M, 0);
	position = strtol(str, NULL, 10);
	printf("position = %d:%d\n", position / 1000 / 60, (position / 1000) % 60);
	memset(str, 0, sizeof(str));
	position = position - step;
	strcpy(mciStr, "play mp3 from ");
	_itoa(position, str, 10);
	strcat(mciStr, str);
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Go backward 10s\n");
}

/*
�ú�����������������

author:����2015141463132
version:1.2
v1.1:�������������ޣ���volume����Ϊȫ�ֱ�������ʼֵ500.
v1.2:�������������ޣ�һ�������ĸĶ�Ϊ200��ʹ�������ڸ��ӿ�ݣ�
*/
void UpVolume()
{
	char str1[10];
	volume = volume + 200;
	strcpy(mciStr, "setaudio mp3 volume to ");
	sprintf(str1, "%d", volume);//��int��ת��Ϊchar��
	strcat(mciStr, str1);
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "status mp3 volume");
	mciSendStringA(mciStr, str, M, 0);
	volume = strtol(str, NULL, 10);
	printf("volume = %u\n", volume);
	printf("Up the volume\n");
}

/*
�ú������ڼ�С������

author:����2015141463132
version:1.2
v1.2:�Ķ���UpVolume���Ӧ��
*/
void DownVolume()
{
	char str1[10];
	volume = volume - 200;
	strcpy(mciStr, "setaudio mp3 volume to ");
	sprintf(str1, "%d", volume);//��int��ת��Ϊchar��
	strcat(mciStr, str1);
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "status mp3 volume");
	mciSendStringA(mciStr, str, M, 0);
	volume = strtol(str, NULL, 10);
	printf("volume = %u\n", volume);
	printf("Down the volume\n");
}

/*
�ú������ھ�����

author:����2015141463132
version:1.0
*/
void SoundOff()
{
	strcpy(mciStr, "setaudio mp3 off");
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Set off\n");
}

/*
�ú������ڹرվ�����

author:����2015141463132
version:1.0
*/
void SoundOn()
{
	strcpy(mciStr, "setaudio mp3 on");
	mciSendStringA(mciStr, 0, 0, 0);
	printf("Set on\n");
}

/*
�˺��������л������б���ʾ

author:��Զ��
v1.0
author:�캬
v1.1:2016/7/28 �޸��˲����б��ȡ�����bug�������ز����б����Ƴ�����LoadMusicList����
*/
void DisplayList(int judge)
{
	int i = 0;
	printf("��ǰ�б�%d\n", judge);
	if (p == 0)
	{
		printf("�����б���û�и���\n");
		return;
	}
	for (int i = 0; i < p; i++)
	{
		printf("%d.\t%s\n", i + 1, (*(musicList + i)).title);
	}
	printf("\n");

	return;
}

/*
�˺������ڲ��ҵ�ǰ�б��ڵĸ���

author:��Զ��2015141463226
version��1.0

*/
Music SearchMusic()
{
	Music music;//
	int i = 0;
	int j = 0;
	char song[50];
	printf("������Ҫ���ҵĸ�������\n");
	scanf("%s", song);
	for (i = 0; i < *H;)
	{
		if (strcmp(song, (*(musicList + i)).title) == 0)
		{
			printf("�Ѳ��ҵ��ø�����\n");
			music = *(musicList + i);
			printf("%s %s\n", (*(musicList + i)).title, (*(musicList + i)).path);
			j = 1;
			break;
		}
		else
		{
			i++;
		}
	}
	if (j != 1)
	{
		printf("δ�ҵ��ø�����\n");
		getchar();
	}
	return music;
}

/*
�˺�������ɾ�������б��еĸ���

author:��Զ��
v1.0
author:�캬
v1.1:2016/7/28 �������б��湦����SaveMusicList����*/
void DelMusic()
{
	int a = 0;
	int i = 0;
	int j = 0;
	int m = 0;
	int n = 0;
	int song = 0;
	DisplayList(judgement);
	printf("��ѡ��Ҫɾ���ĸ�����ţ�\n");
	scanf("%d", &song);
	if (song > 0 && song <= p)
	{
		j = 1;
	}
	if (j != 1)
	{
		printf("δ�ҵ��ø�����\n");
		getchar();
	}
	else
	{
		printf("��ȷ��Ҫɾ���ø����� \n���ǣ�������1�������ǣ�������0��\n");
		scanf_s("%d", &a);
		if (a == 1)
		{
			strcpy((*(musicList + song - 1)).path, "");
			printf("%s��ɾ��\n\n", (*(musicList + song - 1)).title);
			SaveMusicList(currentListNumber);
			LoadMusicList(currentListNumber);
		}
		else if (a == 0)
		{
			getchar();
		}
		else
		{
			printf("�����ָ�����\n");
		}
	}
}

/*
�˺��������޸ĸ�����Ϣ

author:��Զ��
v1.0
author:�캬
v1.1:2016/7/28 �����湦����SaveMusicList����
*/
void ModifyList()
{
	int a = 0;
	int i = 0;
	int j = 0;
	int m = 0;
	int n = 0;
	int choice;
	int song=0;
	DisplayList(judgement);
	printf("��ѡ��Ҫ�޸ĵĸ�����ţ�\n");
	scanf("%d", &song);
	if (song > 0 || song <= p)
	{
		j = 1;
		m = song;
	}
	if (j != 1)
	{
		printf("δ�ҵ��ø�����");
		getchar();
	}
	printf("��ѡ��Ҫ�޸ĵ���Ϣ��\n");//���û�������Ҫ�޸ĵ�����
	printf("1������   2������   3��·��\n");
	scanf("%d", &choice);
	switch (choice)
	{
	case 1:
		printf("�������޸ĺ����Ϣ��\n");
		scanf("%s", &(*(musicList + m - 1)).title);
		getchar();
		break;
	case 2:
		printf("�������޸ĺ����Ϣ��\n");
		scanf("%ld", &(*(musicList + m - 1)).length);
		getchar();
		break;
	case 3:
		printf("�������޸ĺ����Ϣ��\n");
		scanf("%s", &(*(musicList + m - 1)).path);
		getchar();
		break;
	default:
		printf("�Բ���û�и�ѡ�\n");
		break;
	}
	SaveMusicList(currentListNumber);
	LoadMusicList(currentListNumber);
}

/*
�˺��������л��赥

author:��Զ��
v1.0
author:�캬
v1.1:2016/7/28 ���SaveMusicList����
*/
void ChangeList()
{
	printf("��ѡ��Ҫ�л��ĸ赥��\n");
	printf("1��2��3��4��\n");
	scanf_s("%d", &judgement);
	switch (judgement)
	{
	case 1:
		SaveMusicList(currentListNumber);
		currentListNumber = judgement;
		LoadMusicList(currentListNumber);
		DisplayList(currentListNumber);
		break;
	case 2:
		SaveMusicList(currentListNumber);
		currentListNumber = judgement;
		LoadMusicList(currentListNumber);
		DisplayList(currentListNumber);
		break;
	case 3:
		SaveMusicList(currentListNumber);
		currentListNumber = judgement;
		LoadMusicList(currentListNumber);
		DisplayList(currentListNumber);
		break;
	case 4:
		SaveMusicList(currentListNumber);
		currentListNumber = judgement;
		LoadMusicList(currentListNumber);
		DisplayList(currentListNumber);
	default:
		printf("�������");
		getchar();
		break;
	}
}

/*
�ú���������ʾ������Ϣ
music:Ҫ��ʾ��Ϣ�ĸ���

version:1.0
author:�캬2015141463200
*/
void DisplayMusicInfo(Music music)
{
	long length = 0;
	char str[10];
	//��ȡ�ܳ���
	mciSendStringA("status mp3 length", str, M, 0);
	length = strtol(str, NULL, 10);
	printf("�������⣺%s\n", music.title);
	printf("�������ȣ�%d:%d\n", length / 1000 / 60, (length / 1000) % 60);
	printf("����·����%s\n", music.path);
}

/*
�ú������ڸı䲥��ģʽ

version:1.0
author:�캬2015141463200
*/
void ChangePlayMode()
{
	char mode = '0';
	printf("1.���ŵ��� 2.����ѭ�� 3.�б�ѭ�� 4.���ѭ��\n");
	printf("ѡ�񲥷�ģʽ��");
	scanf_s("%c", &mode, 1);
	getchar();
	switch (mode)
	{
	case '1':
		playMode = 1;
		break;
	case '2':
		playMode = 2;
		break;
	case '3':
		playMode = 3;
		break;
	case '4':
		playMode = 4;
		break;
	default:
		printf("û�� %c ѡ��", mode);
		break;
	}
}

/*
�ú������ڲ�����/��һ�׸����������ظø�����ָ��
musicList:�����б�ͷָ��
H:�б��еĸ�������
norl:ֵΪ0ʱ������һ�ף�ֵΪ1ʱ������һ��
currentMusic:��ǰ���ڲ��ŵĸ���

version:1.2
author:�캬2015141463200
V1.1:2016/7/25 �޸��˺����ķ�������ΪMusic*
V1.2:2016/7/27 ����˲���currentMusic
*/
Music* ChangeMusic(Music *musicList, Music currentMusic, int musicQuantity, int norl)
{
	if (musicQuantity <= 0)
	{
		printf("musicQuantity error\n\n");
		return NULL;
	}
	int index = 0;	//��¼��ǰ���Ÿ�������λ��
	for (index = 0; index < musicQuantity;)
	{
		if (strcmp(currentMusic.path, (*(musicList + index)).path) == 0)
		{
			break;
		}
		index++;
	}
	//char mciStr[100];
	if (norl == 0)
	{
		if (index == 0)
		{
			index = musicQuantity - 1;
		}
		else if (musicQuantity == 1)
		{
			index = 0;
		}
		else
		{
			index--;
		}
		/*
		//ֹͣ
		strcpy_s(mciStr, 9, "stop mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//�ر�
		strcpy_s(mciStr ,9, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//������һ��
		strcpy_s(mciStr, 6, "open ");
		strcat(mciStr, (*(musicList + index)).path);
		strcat(mciStr, " alias mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		*/

		return (musicList + index);
	}
	else if (norl == 1)
	{
		if (index == musicQuantity - 1)
		{
			index = 0;
		}
		else if (musicQuantity == 1)
		{
			index = 0;
		}
		else
		{
			index++;
		}
		/*
		//ֹͣ
		strcpy(mciStr, "stop mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//�ر�
		strcpy(mciStr, "close mp3");
		mciSendStringA(mciStr, 0, 0, 0);

		//������һ��
		strcpy(mciStr, "open ");
		strcat(mciStr, (*(musicList + index)).path);
		strcat(mciStr, " alias mp3");
		mciSendStringA(mciStr, 0, 0, 0);
		*/
		return (musicList + index);
	}
	else
	{
		return NULL;
	}
}

/*
�ú��������û���ָ��·���е�MP3�ļ����м���������������б������ص�ǰ�����б��ָ�롢�޸Ĳ����б��еĸ�������
musicList:�����б��ָ��
H:�����б��еĸ�����Ŀ��ָ��

version:1.2
author:�캬2015141463200
v1.0:2016/7/21
v1.1:2016/7/25 �޸ķ���ֵΪMusic*���͡���choice�����޸�Ϊint��������H�������޸�Ϊint*
v1.2:2016/7/26 �޸����޷�����ո��bug���޸���ȫ����ӳ����bug
*/
Music* SearchLocalMusic(Music *musicList,int *H)
{
	//�ýṹ���Ÿ�����·���ͱ���
	typedef struct
	{
		char filePath[100];
		char fileName[50];
	}TempFileInfo;

	TempFileInfo *list;		//��ʱ�б�����������ĸ���·���ͱ���
	Music *musicListBackup = musicList;			//����musicList
	int musicQuantityBackup = *H;	//����musicQuantity
	char path[100];			//��¼�û������·��
	char searchPath[100];	//����_findfirst��������MP3����
	char mciStr[100];		//mciָ��
	int musicCount = 0;		//��¼Ŀ��·���еĸ�������
	int currentListCount = p;			//��¼��ǰ�����б��еĸ�����Ŀ
	int addCount = 0;		//��¼��Ӹ���������
	int choice = 0;			//�����ͱ������ѡ��
	printf("����MP3�ļ������ļ��е�·����\n");
	scanf("%[^\n]", path);
	getchar();
	//Ϊ·��������'\'
	if (path[strlen(path) - 1] != '\\')
	{
		strcat(path, "\\");
	}
	if (_access(path, 0) != 0)
	{
		printf("·��������\n");
		return musicList;
	}

	//����·���еĸ���
	strcpy(searchPath, path);
	strcat(searchPath, "*.mp3");
	long Handle;
	_finddata_t FileInfo;
	Handle = _findfirst(searchPath, &FileInfo);
	if (Handle == -1)
	{
		printf("û���ҵ�mp3�ļ�\n");
		return musicList;
	}
	else
	{
		musicCount = 1;
		//����MP3�ļ���Ϊlist����ռ����ļ�·��������
		list = (TempFileInfo*)calloc(1, sizeof(TempFileInfo));
		strcpy((*(list)).filePath, path);
		strcat((*(list)).filePath, FileInfo.name);
		strcpy((*(list)).fileName, FileInfo.name);
		printf("1.\t%s\n", FileInfo.name);

		for (; _findnext(Handle, &FileInfo) == 0;)
		{
			musicCount++;
			//ÿ��һ��MP3�ļ��ͽ���ʱ�б�ռ�����һ��TempFileInfo��С
			list = (TempFileInfo*)realloc(list, musicCount*sizeof(TempFileInfo));
			if (list == NULL)
			{
				printf("��������\n\n");
				return musicList;
			}
			//��·��д����ʱ�б�
			strcpy((*(list + musicCount - 1)).filePath, path);
			strcat((*(list + musicCount - 1)).filePath, FileInfo.name);
			//list test(passed)
			//printf("%s\n", (*(list + musicCount - 1)).filePath);
			//������д����ʱ�б�
			strcpy((*(list + musicCount - 1)).fileName, FileInfo.name);
			printf("%d.\t%s\n", musicCount, FileInfo.name);
		}

		//test list(passed)
		//for (int i = 0; i < musicCount; i++)
		//{
		//	printf("%s\n%s\n", (*(list + i)).fileName, (*(list + i)).filePath);
		//}

		//�����û���·���еĸ���������б�
		while (true)
		{
			printf("����Ҫ��ӽ������б�ĸ������(ȫ���������-1,�������0)��");
			scanf("%d", &choice);
			getchar();
			//int digitChoice = choice - '0';
			if (choice == 0)
			{
				printf("������\n\n");

				//ͨ��ָ���޸Ĳ����б�ĸ�����Ŀ
				*H = currentListCount;
				return musicList;
			}
			else if (choice > 0 && choice <= musicCount)
			{
				addCount++;
				//Ϊ�����б�����addCount��Music��С
				musicList = (Music*)realloc(musicList, (*H + addCount)*sizeof(Music));
				if (musicList == NULL)
				{
					printf("��ӳ���\n\n");
					free(list);//�ͷ�list
					list = NULL;

					//����ӳ������޸Ĳ����б��еĸ�����Ŀ����ͬ
					return musicListBackup;
				}
				currentListCount++;//��ǰ�����б������Ŀ+1
								   //�򿪸����ļ�
				strcpy(mciStr, "open ");
				strcat(mciStr, (*(list + choice - 1)).filePath);
				strcat(mciStr, " alias mp3");
				mciSendStringA(mciStr, 0, 0, 0);
				//��ȡ�ܳ���
				char str[20] = "";
				strcpy(mciStr, "status mp3 length");
				mciSendStringA(mciStr, str, 20, 0);
				//length test
				//printf("%s\n", str);
				//printf("%ld\n", strtol(str, NULL, 10));
				//�򲥷��б�����������Ϣ
				//����
				(*(musicList + *H + addCount - 1)).length = strtol(str, NULL, 10);
				//����
				strcpy((*(musicList + *H + addCount - 1)).title, (*(list + choice - 1)).fileName);
				//·��
				strcpy((*(musicList + *H + addCount - 1)).path, (*(list + choice - 1)).filePath);

				//test(passed)
				//printf("%s\n", (*(*musicList + H + addCount - 1)).title);
				//printf("%s\n", (*(*musicList + H + addCount - 1)).path);
				//�رո����ļ�
				strcpy(mciStr, "close mp3");
				mciSendStringA(mciStr, 0, 0, 0);
				printf("%s �����\n", (*(list + choice - 1)).fileName);
			}
			else if (choice == -1)//ȫ�����
			{
				for (int i = 0; i < musicCount; i++)
				{
					addCount++;
					//����ռ�
					musicList = (Music*)realloc(musicList, (*H + addCount)*sizeof(Music));
					if (musicList == NULL)
					{
						printf("��ӳ���\n\n");
						free(list);//�ͷ�list
						list = NULL;

						return musicListBackup;
					}
					//�򿪸����ļ�
					strcpy(mciStr, "open ");
					strcat(mciStr, (*(list + choice - 1)).filePath);
					strcat(mciStr, " alias mp3");
					mciSendStringA(mciStr, 0, 0, 0);
					//��ȡ�ܳ���
					char str[20] = "";
					strcpy(mciStr, "status mp3 length");
					mciSendStringA(mciStr, str, 20, 0);
					//�򲥷��б�����������Ϣ
					//����
					(*(musicList + *H + addCount - 1)).length = strtol(str, NULL, 10);
					//����
					strcpy((*(musicList + *H+ addCount - 1)).title, (*(list + i)).fileName);
					//·��
					strcpy((*(musicList + *H + addCount - 1)).path, (*(list + i)).filePath);
					//�رո����ļ�
					strcpy(mciStr, "close mp3");
					mciSendStringA(mciStr, 0, 0, 0);
				}
				currentListCount += musicCount;//��ǰ������Ŀ����·���еĸ�����Ŀ
				printf("ȫ��������\n\n");

				free(list);//�ͷ�list
				list = NULL;

				*H = currentListCount;
				return musicList;
			}
			else
			{
				printf("��Чѡ��\n");
			}
		}
	}

	*H = currentListCount;
	return musicList;
}

/*
�˺�����txt�ļ��еĸ����б��ȡ��MusicList��

version:1.0
author:�캬2015141463200
*/
void LoadMusicList(int listNumber)
{
	Music* musicListBackup = musicList;
	FILE *fp;
	char filePath[100] = "";
	p = 0;
	switch (listNumber)
	{
	case 1:
		strcpy(filePath, "mp3.1.txt");
		break;
	case 2:
		strcpy(filePath, "mp3.2.txt");
		break;
	case 3:
		strcpy(filePath, "mp3.3.txt");
		break;
	default:
		strcpy(filePath, "mp3.4.txt");
		break;
	}

	fp = fopen(filePath, "a+");
	if (fp == NULL)
	{
		printf("�����б����ʧ��\n\n");
		return;
	}

	//��ִ��һ�ζ��ļ�
	musicListBackup = musicList;	//����musicList
	musicList = (Music*)realloc(musicList, (p + 1)*sizeof(Music));
	if (musicList == NULL)
	{
		printf("�����б���ش���\n\n");
		musicList = musicListBackup;
	}

	if (!feof(fp))
	{
		char temp[100];
		fgets((*(musicList + p)).path, 100, fp);
		if (feof(fp))
		{
			temp[0] = '\0';
		}
		else
		{
			strcpy(temp, (*(musicList + p)).path);
			if ((*(musicList + p)).path[strlen((*(musicList + p)).path) - 1] == '\n')
			{
				(*(musicList + p)).path[strlen((*(musicList + p)).path) - 1] = '\0';
			}
			fgets((*(musicList + p)).title, 50, fp);
			strcpy(temp, (*(musicList + p)).title);
			if ((*(musicList + p)).title[strlen((*(musicList + p)).title) - 1] == '\n')
			{
				(*(musicList + p)).title[strlen((*(musicList + p)).title) - 1] = '\0';
			}
		}
	}
	//Ȼ�����ѭ��������ʹ��feof(fp)���ѭ��һ��
	while (!feof(fp))
	{
		p++;
		musicListBackup = musicList;	//����musicList
		musicList = (Music*)realloc(musicList, (p + 1)*sizeof(Music));
		if (musicList == NULL)
		{
			printf("�����б���ش���\n\n");
			musicList = musicListBackup;
		}

		char temp[100];
		fgets((*(musicList + p)).path, 100, fp);
		if (feof(fp))
		{
			temp[0] = '\0';
			break;
		}
		strcpy(temp, (*(musicList + p)).path);
		if ((*(musicList + p)).path[strlen((*(musicList + p)).path) - 1] == '\n')
		{
			(*(musicList + p)).path[strlen((*(musicList + p)).path) - 1] = '\0';
		}
		fgets((*(musicList + p)).title, 50, fp);
		strcpy(temp, (*(musicList + p)).title);
		if ((*(musicList + p)).title[strlen((*(musicList + p)).title) - 1] == '\n')
		{
			(*(musicList + p)).title[strlen((*(musicList + p)).title) - 1] = '\0';
		}
	}

	fclose(fp);
	return;
}

/*
�˺������ڱ����޸ĺ�Ĳ����б�

version:1.0
author:�캬2015141463200
*/
void SaveMusicList(int listNumber)
{
	FILE *fp;
	char filePath[100] = "";
	switch (listNumber)
	{
	case 1:
		strcpy(filePath, "mp3.1.txt");
		break;
	case 2:
		strcpy(filePath, "mp3.2.txt");
		break;
	case 3:
		strcpy(filePath, "mp3.3.txt");
		break;
	default:
		strcpy(filePath, "mp3.txt");
		break;
	}

	fp = fopen(filePath, "w+");
	if (fp == NULL)
	{
		printf("�����б���ʧ��\n\n");
		return;
	}
	for (int i = 0; i < p; i++)
	{
		if (strcmp((*(musicList + i)).path, "") != 0)
		{
			fputs((*(musicList + i)).path, fp);
			fputs("\n", fp);
			fputs((*(musicList + i)).title, fp);
			fputs("\n", fp);
		}
	}

	fclose(fp);
	return;
}

/*
�˺������ڸ���ģʽ�Ĳ���

version:1.0
author:�캬
v1.0:2016/7/27
*/
void Play()
{
	playStatu = 1;
	int musicIndex = 0;
	//char str[20];
	currentMusic = *(musicList + musicIndex);
	srand(time(NULL));
	while (playStatu != 0)
	{
		if (playMode == 4)
		{
			musicIndex = rand() % p;
			currentMusic = *(musicList + musicIndex);
		}
		//PLAYMUSIC = CreateThread(NULL, 0, PlayMusicThread, NULL, 0, NULL);
		//checkStop = CreateThread(NULL, 0, checkStopFun, NULL, 0, NULL);

		//unsigned long position = 0;
		//unsigned long length = 1;
		//char mciStr[50];
		//SetTimer(NULL, 1, 1000, (TIMERPROC)checkStopFun);
		playStatu = PlayMusic(currentMusic.path);
		//while (playStatu == 1)
		//{
		//	Sleep(1000);
		//}
		//TerminateThread(PlayMusicThread, NULL);
		//CloseHandle(PlayMusicThread);
		if (playStatu == 2)
		{
			playStatu = 1;
		}
		else if (playMode == 1)
		{
			playStatu = 0;
		}
		else if (playMode == 2)
		{
			playStatu = 1;
		}
		else if (playMode == 3)
		{
			currentMusic = *ChangeMusic(musicList, currentMusic, p, 1);
		}
		else
		{
			playStatu = 0;
		}
	}
	
}


void checkStopFun()
{
	while (true)
	{
		Sleep(1000);
		//��ȡ����λ��
		strcpy(mciStr, "status mp3 mode");
		mciSendStringA(mciStr, str, 20, 0);
		if (strcmp(str,"stopped")==0)
		{
			playStatu = 0;
			break;
		}
	}

	return;
}


DWORD WINAPI PlayMenuThread(LPVOID lParam)
{
	int playStatu = 1;
	while (playStatu == 1)
	{
		playStatu = PlayMenu();
	}
	return 0;
}