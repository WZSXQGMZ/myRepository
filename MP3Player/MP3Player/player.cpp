#pragma once
#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE
#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <windows.h> 
#include <io.h>
#include <ctype.h>
#include "MP3Player.h"

#pragma comment(lib, "WINMM.LIB") 

int playMode = 0;
Music currentMusic;

void displayMusicInfo(Music music);
void changePlayMode();
Music* changeMusic(Music *musicList, int musicQuantity, int norl);
Music* searchLocalMusic(Music *musicList, int *musicQuantity);

/*
//Test
Music *musicList;//�����б�
int musicNumber = 0;
bool searchLocalMusicTest();
bool changeMusicTest();
void main()
{
	searchLocalMusicTest();
	system("pause");
	changeMusicTest();
	system("pause");

	free(musicList);
	musicList = NULL;
}
bool searchLocalMusicTest()
{
	musicList = (Music*)calloc(1, sizeof(Music));
	musicList = searchLocalMusic(musicList, &musicNumber);
	for (int i = 0; i < musicNumber; i++)
	{
		displayMusicInfo(*(musicList + i));
	}

	return true;
}
bool changeMusicTest()
{
	char mciStr[100] = "";
	currentMusic = *(musicList);
	displayMusicInfo(currentMusic);

	strcpy_s(mciStr, 6, "open ");
	strcat(mciStr, currentMusic.path);
	strcat(mciStr, " alias mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "play mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	system("pause");

	currentMusic = *changeMusic(musicList, musicNumber, 0);
	displayMusicInfo(currentMusic);
	strcpy_s(mciStr, 6, "open ");
	strcat(mciStr, currentMusic.path);
	strcat(mciStr, " alias mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "play mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	system("pause");

	currentMusic = *changeMusic(musicList, musicNumber, 1);
	displayMusicInfo(currentMusic);
	strcpy_s(mciStr, 6, "open ");
	strcat(mciStr, currentMusic.path);
	strcat(mciStr, " alias mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	strcpy(mciStr, "play mp3");
	mciSendStringA(mciStr, 0, 0, 0);
	system("pause");

	return true;
}
//TestEnd
*/

/*
�ú���������ʾ������Ϣ
music:Ҫ��ʾ��Ϣ�ĸ���

author:�캬2015141463200
version:1.0
*/
void displayMusicInfo(Music music)
{
	printf("�������⣺%s\n", music.title);
	printf("�������ȣ�%d:%d\n", music.length / 1000 / 60, (music.length / 1000) % 60);
	printf("����·����%s\n\n", music.path);
}

/*
�ú������ڸı䲥��ģʽ

author:�캬2015141463200
version:1.0
*/
void changePlayMode()
{
	char mode='0';
	while (mode != '1' || mode != '2' || mode != '3' || mode != '4')
	{
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
			printf("No choice of %c", mode);
			break;
		}
	}
}

/*
�ú������ڲ�����/��һ�׸����������ظø�����ָ��
musicList:�����б�ͷָ��
musicQuantity:�б��еĸ�������
norl:ֵΪ0ʱ������һ�ף�ֵΪ1ʱ������һ��

author:�캬2015141463200
version:1.1
V1.1:2016/7/25 �޸��˺����ķ�������ΪMusic*
*/
Music* changeMusic(Music *musicList, int musicQuantity, int norl)
{
	if (musicQuantity <= 0)
	{
		printf("musicQuantity error\n\n");
		return NULL;
	}
	int index = 0;	//��¼��ǰ���Ÿ�������λ��
	for (index = 0; index < musicQuantity;)
	{
		if (strcmp(currentMusic.path, (*(musicList + index)).path)==0)
		{
			break;
		}
		index++;
	}
	char mciStr[100];
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
musicQuantity:�����б��еĸ�����Ŀ��ָ��

author:�캬2015141463200
version:1.2
v1.0:2016/7/21
v1.1:2016/7/25�޸ķ���ֵΪMusic*���͡���choice�����޸�Ϊint��������musicQuantity�������޸�Ϊint*
v1.2:2016/7/26�޸����޷�����ո��bug���޸���ȫ����ӳ����bug
*/
Music* searchLocalMusic(Music *musicList, int *musicQuantity)
{
	//�ýṹ���Ÿ�����·���ͱ���
	typedef struct
	{
		char filePath[100];
		char fileName[50];
	}TempFileInfo;

	TempFileInfo *list;		//��ʱ�б�����������ĸ���·���ͱ���
	Music *musicListBackup = musicList;			//����musicList
	int musicQuantityBackup = *musicQuantity;	//����musicQuantity
	char path[100];			//��¼�û������·��
	char searchPath[100];	//����_findfirst��������MP3����
	char mciStr[100];		//mciָ��
	int musicCount = 0;		//��¼Ŀ��·���еĸ�������
	int currentListCount = musicCount;			//��¼��ǰ�����б��еĸ�����Ŀ
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
				printf("������\n");

				//ͨ��ָ���޸Ĳ����б�ĸ�����Ŀ
				*musicQuantity = currentListCount;
				return musicList;
			}
			else if (choice > 0 && choice <= musicCount)
			{
				addCount++;
				//Ϊ�����б�����addCount��Music��С
				musicList = (Music*)realloc(musicList, (*musicQuantity + addCount)*sizeof(Music));
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
				(*(musicList + *musicQuantity + addCount - 1)).length = strtol(str, NULL, 10);
				//����
				strcpy((*(musicList + *musicQuantity + addCount - 1)).title, (*(list + choice - 1)).fileName);
				//·��
				strcpy((*(musicList + *musicQuantity + addCount - 1)).path, (*(list + choice - 1)).filePath);

				//test(passed)
				//printf("%s\n", (*(*musicList + musicQuantity + addCount - 1)).title);
				//printf("%s\n", (*(*musicList + musicQuantity + addCount - 1)).path);
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
					musicList = (Music*)realloc(musicList, (*musicQuantity + addCount)*sizeof(Music));
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
					(*(musicList + *musicQuantity + addCount - 1)).length = strtol(str, NULL, 10);
					//����
					strcpy((*(musicList + *musicQuantity + addCount - 1)).title, (*(list + i)).fileName);
					//·��
					strcpy((*(musicList + *musicQuantity + addCount - 1)).path, (*(list + i)).filePath);
					//�رո����ļ�
					strcpy(mciStr, "close mp3");
					mciSendStringA(mciStr, 0, 0, 0);
				}
				currentListCount += musicCount;//��ǰ������Ŀ����·���еĸ�����Ŀ
				printf("ȫ��������\n");

				free(list);//�ͷ�list
				list = NULL;

				*musicQuantity = currentListCount;
				return musicList;
			}
			else
			{
				printf("��Чѡ��\n");
			}
		}
	}

	*musicQuantity = currentListCount;
	return musicList;
}
