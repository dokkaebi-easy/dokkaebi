import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import Stack from '@mui/material/Stack';
import AutorenewIcon from '@mui/icons-material/Autorenew';
import { v4 as uuid } from 'uuid';
import { Git } from 'Components/MDClass/GitData/GitData';
import axios from 'axios';
import FormHelperText from '@mui/material/FormHelperText';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';

interface GitProps {
  gitData: Git;
}

interface TestConnectionAxios {
  status: string;
}

export default function ConnectCheck({ gitData }: GitProps) {
  const projectName = useSettingStore((state) => state.projectName);
  const [secretToken, setSecretToken] = useState(gitData.secretToken);
  const [domain, setDomain] = useState('');
  const [testConnectionWord, setTestConnectionWord] = useState('');

  const handleCreateClick = () => {
    const token = uuid();
    setSecretToken(token);
    gitData.secretToken = token;
  };

  const handleDomainChange = (event: any) => {
    setDomain(event.target.value);
  };

  const handleTestConnectionClick = () => {
    const data = {
      accessTokenId: gitData.accessTokenId,
      branchName: gitData.branchName,
      hostUrl: gitData.hostUrl,
      projectId: gitData.gitProjectId,
      repositoryUrl: gitData.repositoryUrl,
    };

    axios.post('/api/git/testConnection', data).then((res) => {
      const word = res.data as TestConnectionAxios;
      setTestConnectionWord(word.status);
    });
  };

  return (
    <Box my={3}>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper
          sx={{
            padding: 1,
            textAlign: 'center',
            width: 150,
            color: ' white',
            background: 'linear-gradient(195deg, #666, #191919)',
          }}
        >
          Connetion Check
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography>Web Hook URL</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                variant="outlined"
                size="small"
                defaultValue={domain}
                onChange={handleDomainChange}
                sx={{ my: 1 }}
              />
              <FormHelperText id="component-helper-text">
                (※ 도메인 주소를 적어주세요. ex) http://
                <span style={{ color: 'red' }}>k6s205.p.ssafy.io:8482</span>
                /api/project/hook/Dockerby)
              </FormHelperText>
            </Grid>
            <Grid item xs={2} sx={{ my: 'auto' }} />

            <Grid item xs={10} sx={{ my: 'auto' }}>
              <Typography>
                {domain
                  ? `http://${domain}/api/project/hook/${projectName}`
                  : ''}
              </Typography>
            </Grid>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography>Secret Token</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                disabled
                variant="outlined"
                size="small"
                value={secretToken}
                sx={{ my: 1 }}
              />
              <FormHelperText id="component-helper-text">
                (※ 다른 곳에 저장해두세요.)
              </FormHelperText>
            </Grid>

            <Grid item xs={6} sx={{ my: 'auto' }}>
              {testConnectionWord.length > 10 ? (
                <Typography sx={{ color: 'red' }}>
                  {testConnectionWord ? `Result : ${testConnectionWord} ` : ''}
                </Typography>
              ) : (
                <Typography sx={{ color: 'green' }}>
                  {testConnectionWord ? `Result : ${testConnectionWord} ` : ''}
                </Typography>
              )}
            </Grid>
            <Grid item xs={6} sx={{ my: 'auto' }}>
              <Stack direction="row" justifyContent="flex-end" spacing={2}>
                <Button
                  variant="outlined"
                  startIcon={<AddIcon />}
                  size="small"
                  onClick={handleCreateClick}
                  sx={{ color: 'black', borderColor: 'black' }}
                >
                  Create
                </Button>
                <Button
                  variant="outlined"
                  startIcon={<AutorenewIcon />}
                  size="small"
                  onClick={handleTestConnectionClick}
                  sx={{ color: 'black', borderColor: 'black' }}
                >
                  test Connection
                </Button>
              </Stack>
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </Box>
  );
}
