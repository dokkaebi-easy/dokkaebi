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
  const [accessTokenId, setAccessTokenId] = useState('');
  const [accessTokenIds, setAccessTokenIds] = useState<string[]>([]);
  const [open, setOpen] = useState(false);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleItemClickProps = (index: number) => {
    setAccessTokenId(accessTokenIds[index]);
    gitData.accessTokenId = index + 1;
  };

  const handleHostURLChange = (event: any) => {
    setHostURL(event.target.value);
    gitData.hostUrl = event.target.value;
  };

  const handleAxiosProps = (data: ResponseIdName[]) => {
    const arr = data.map((value) => value.name);
    setAccessTokenIds([...arr]);
    setAccessTokenId(arr[gitData.accessTokenId - 1]);
  };

  useEffect(() => {
    axios.get('/api/git/tokens').then((res) => {
      const data = res.data as ResponseIdName[];
      const arr = data.map((value) => value.name);
      setAccessTokenIds([...arr]);
      setAccessTokenId(arr[gitData.accessTokenId - 1]);
    });

    return () => {
      setAccessTokenIds([]);
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
        <Paper sx={{ padding: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography>Host URL</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                label="ex) https://lab.ssafy.com/"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="ex) https://lab.ssafy.com/"
                defaultValue={hostURL}
                onChange={handleHostURLChange}
              />
            </Grid>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography>Access Token</Typography>
            </Grid>
            <Grid item xs={5}>
              <SelectItem
                defaultValue={accessTokenId}
                label="Access Token"
                Items={accessTokenIds}
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
                Add
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
