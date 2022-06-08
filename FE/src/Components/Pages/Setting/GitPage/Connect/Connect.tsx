import { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import ConnetctModal from 'Components/Pages/Setting/GitPage/ConnetctModal/ConnetctModal';
import { Git } from 'Components/MDClass/GitData/GitData';
import axios from 'axios';
import { ResponseIdName } from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';

interface GitProps {
  gitData: Git;
}

export default function GitLabConnect({ gitData }: GitProps) {
  const [hostURL, setHostURL] = useState(gitData.hostUrl);
  const [accessTokenName, setAccessTokenName] = useState('');
  const [accessTokenNames, setAccessTokenNames] = useState<ResponseIdName[]>(
    [],
  );
  // const [accessTokenId, setAccessTokenId] = useState('');
  // const [accessTokenIds, setAccessTokenIds] = useState<string[]>([]);
  const [open, setOpen] = useState(false);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleItemClickProps = (index: number) => {
    setAccessTokenName(accessTokenNames[index].name);
    gitData.accessTokenId = accessTokenNames[index].id;
  };

  const handleHostURLChange = (event: any) => {
    setHostURL(event.target.value);
    gitData.hostUrl = event.target.value;
  };

  const handleAxiosProps = (data: ResponseIdName[]) => {
    setAccessTokenNames([...data]);
    // setAccessTokenName(arr[gitData.accessTokenId - 1]);
  };

  useEffect(() => {
    axios.get('/api/git/tokens').then((res) => {
      const data = res.data as ResponseIdName[];
      setAccessTokenNames([...data]);

      data.map((value) => {
        if (value.id === gitData.accessTokenId) {
          setAccessTokenName(value.name);
        }
        return value;
      });
    });

    return () => {
      setAccessTokenNames([]);
    };
  }, []);

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
          Connect
        </Paper>
      </Box>
      <Box>
        <Paper
          sx={{ padding: 3, pt: 4, borderWidth: 3 }}
          elevation={0}
          variant="outlined"
        >
          <Grid container spacing={2}>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography fontFamily="Noto Sans KR" fontSize={20}>
                기본 도메인 URL
              </Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                label="Domain URL"
                InputLabelProps={{
                  sx: { color: 'rgb(200,200,200)' },
                }}
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                InputProps={{ sx: { fontWeight: 'bold' } }}
                defaultValue={hostURL}
                onChange={handleHostURLChange}
              />
            </Grid>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography fontFamily="Noto Sans KR" fontSize={20}>
                Access Token
              </Typography>
            </Grid>
            <Grid item xs={5}>
              <SelectItem
                defaultValue={accessTokenName}
                label="Access Token"
                Items={accessTokenNames.map((value) => value.name)}
                Click={handleItemClickProps}
              />
            </Grid>
            <Grid item xs={2} sx={{ marginBottom: 1, marginTop: 'auto' }}>
              <Button
                variant="outlined"
                startIcon={<AddIcon />}
                size="small"
                onClick={handleOpen}
              >
                추가
              </Button>
              <ConnetctModal
                open={open}
                Close={handleClose}
                Change={handleAxiosProps}
              />
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </Box>
  );
}
