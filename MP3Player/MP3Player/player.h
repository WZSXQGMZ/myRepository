#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE
#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <windows.h> 
#include <io.h>
#include <ctype.h>

#pragma comment(lib, "WINMM.LIB") 

typedef struct
{
	char title[50];
	long length;
	char path[100];
}Music;
int playMode = 0;
Music currentMusic;

void displayMusicInfo(Music music);
void changePlayMode();
Music* changeMusic(Music *musicList, int musicQuantity, int norl);
int searchLocalMusic(Music *musicList, int musicQuantity);

/*
�ú���������ʾ������Ϣ

author:�캬2015141463200
version:1.0
*/
void displayMusicInfo(Music music)
{
	printf("�������⣺%s\n", music.title);
	printf("�������ȣ�%d:%d\n", music.length / 1000 / 60, (music.length / 1000) % 60);
	printf("����·����%s\n", music.path);
}

/*
�ú������ڸı䲥��ģʽ

author:�캬2015141463200
version:1.0
*/
void changePlayMode()
{
	char mode;
	while (mode != '1' || mode != '2' || mode != '3' || mode != '4')
	{
		printf("1.���ŵ��� 2.����ѭ�� 3.�б�ѭ�� 4.���ѭ��\n");
		printf("ѡ�񲥷�ģʽ��");
		scanf_s("%c", &mode,1);
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
version:1.0
*/
Music* changeMusic(Music *musicList, int musicQuantity, int norl)
{
	if (musicQuantity <= 0)
	{
		printf("musicQuantity error\n\n");
		return NULL;
	}
	int index = 0;	//��¼��ǰ���Ÿ�������λ��
	char mciStr[100];
	if (norl == 0)
	{
		for (index = 0; index < musicQuantity; index++)
		{
			if (strcmp(currentMusic.path, (*(musicList + index)).path))
			{
				break;
			}
		}

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

		return (musicList + index);
	}
	else if (norl == 1)
	{
		for (int i = 0; i < musicQuantity; i++)
		{
			if (strcmp(currentMusic.path, (*(musicList + i)).path))
			{
				break;
			}
		}

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

		return (musicList + index);
	}
	else
	{
		return;
	}
}

/*
�ú��������û���ָ��·���е�MP3�ļ����м�����������ʧ���򷵻�-1���������ɹ��򷵻�0����û���ļ��򷵻�1
musicList:�����б��ָ��
musicQuantity:�����б��еĸ�����Ŀ

author:�캬2015141463200
version:1.0
*/
int searchLocalMusic(Music *musicList, int musicQuantity)
{
	//�ýṹ���Ÿ�����·���ͱ���
	typedef struct
	{
		char filePath[100];
		char fileName[50];
	}TempFileInfo;

	TempFileInfo *list;		//��ʱ�б�����������ĸ���·���ͱ���
	char path[100];			//��¼�û������·��
	char searchPath[100];	//����_findfirst��������MP3����
	char mciStr[100];		//mciָ��
	int musicCount = 0;		//��¼Ŀ��·���еĸ�������
	char choice;			//���ַ��������ѡ��
	printf("����MP3�ļ������ļ��е�·����\n");
	scanf("%s", path);
	if (access(path, 0) != 0)
	{
		printf("·��������\n");
		return;
	}

	//����·���еĸ���
	strcpy(searchPath, path);
	strcat(searchPath, "*.txt");
	long Handle;
	_finddata_t FileInfo;
	if ((Handle = _findfirst(searchPath, &FileInfo)) == -1L)
	{
		printf("û���ҵ�mp3�ļ�\n");
		return 1;
	}
	else
	{
		musicCount = 1;
		//����MP3�ļ���Ϊlist����ռ����ļ�·��������
		list = (TempFileInfo*)calloc(1, sizeof(TempFileInfo));
		strcpy((*(list)).filePath, path);
		strcat((*(list)).filePath, FileInfo.name);
		strcpy((*(list)).fileName, FileInfo.name);
		printf("1. %s", FileInfo.name);

		for (; _findnext(Handle, &FileInfo) == 0;)
		{
			musicCount++;
			//ÿ��һ��MP3�ļ��ͽ���ʱ�б�ռ�����һ��TempFileInfo��С
			list = (TempFileInfo*)realloc(list, musicCount*sizeof(TempFileInfo));
			if (list == NULL)
			{
				printf("��������\n\n");
				return -1;
			}
			//��·��д����ʱ�б�
			strcpy((*(list + musicCount - 1)).filePath, path);
			strcat((*(list + musicCount - 1)).filePath, FileInfo.name);
			//������д����ʱ�б�
			strcpy((*(list + musicCount - 1)).fileName, FileInfo.name);
			printf("%d. %s\n", musicCount, FileInfo.name);
		}

		//�����û���·���еĸ���������б�
		while (true)
		{
			int addCount = 0;//��¼��Ӹ���������
			printf("����Ҫ��ӽ������б�ĸ������(ȫ��������롰a��,�������0)��");
			scanf("%c", &choice);
			int digitChoice = choice - '0';
			if (digitChoice == 0)
			{
				printf("������\n");
				return 0;
			}
			else if (digitChoice > 0 && digitChoice <= musicCount)
			{
				addCount++;
				//Ϊ�����б�����addCount��Music��С
				musicList = (Music*)realloc(musicList, (musicQuantity + addCount)*sizeof(Music));
				if (musicList == NULL)
				{
					printf("��ӳ���\n\n");
					free(list);//�ͷ�list
					list = NULL;
					return -1;
				}
				//�򿪸����ļ�
				strcpy(mciStr, "open ");
				strcat(mciStr, (*(list + digitChoice - 1)).filePath);
				strcat(mciStr, " alias mp3");
				mciSendStringA(mciStr, 0, 0, 0);
				//��ȡ�ܳ���
				char str[20] = "";
				strcpy(mciStr, "status mp3 length");
				mciSendStringA(mciStr, str, 20, 0);
				//�򲥷��б�����������Ϣ
				//����
				(*(musicList + musicQuantity + addCount - 1)).length = strtol(str, NULL, 10);
				//����
				strcpy((*(musicList + musicQuantity + addCount - 1)).title, (*(list + digitChoice - 1)).fileName);
				//·��
				strcpy((*(musicList + musicQuantity + addCount - 1)).path, (*(list + digitChoice - 1)).filePath);
				//�رո����ļ�
				strcpy(mciStr, "close mp3");
				mciSendStringA(mciStr, 0, 0, 0);
				printf("%s �����\n", (*(list + digitChoice - 1)).fileName);
			}
			else if (choice == 'a')//ȫ�����
			{
				musicList = (Music*)realloc(musicList, (musicQuantity + musicCount)*sizeof(Music));
				if (musicList == NULL)
				{
					printf("��ӳ���\n\n");
					free(list);//�ͷ�list
					list = NULL;
					return -1;
				}
				for (int i = 0; i < musicCount; i++)
				{
					//�򿪸����ļ�
					strcpy(mciStr, "open ");
					strcat(mciStr, (*(list + digitChoice - 1)).filePath);
					strcat(mciStr, " alias mp3");
					mciSendStringA(mciStr, 0, 0, 0);
					//��ȡ�ܳ���
					char str[20] = "";
					strcpy(mciStr, "status mp3 length");
					mciSendStringA(mciStr, str, 20, 0);
					//�򲥷��б�����������Ϣ
					//����
					(*(musicList + musicQuantity + addCount - 1)).length = strtol(str, NULL, 10);
					//����
					strcpy((*(musicList + musicQuantity + addCount - 1)).title, (*(list + digitChoice - 1)).fileName);
					//·��
					strcpy((*(musicList + musicQuantity + addCount - 1)).path, (*(list + digitChoice - 1)).filePath);
					//�رո����ļ�
					strcpy(mciStr, "close mp3");
					mciSendStringA(mciStr, 0, 0, 0);
				}

				free(list);//�ͷ�list
				list = NULL;
				return 0;
			}
			else
			{
				printf("��Чѡ��\n");
			}
		}
	}

	return 0;
}
