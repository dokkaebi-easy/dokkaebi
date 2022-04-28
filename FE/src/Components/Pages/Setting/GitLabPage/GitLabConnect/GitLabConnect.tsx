import { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import Stack from '@mui/material/Stack';
import ConnetctModal from 'Components/Pages/Setting/GitLabPage/ConnetctModal/ConnetctModal';
import { Git } from 'Components/MDClass/GitData/GitData';
import axios from 'axios';
import { ResponseIdName } from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';

interface GitProps {
  gitData: Git;
}

export default function GitLabConnect({ gitData }: GitProps) {
  const [name, setName] = useState(gitData.name);
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

  const handleNameChange = (event: any) => {
    setName(event.target.value);
    gitData.name = event.target.value;
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
    axios.get('api/git/tokens').then((res) => {
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
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography>name</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                id="outlined-basic1"
                label="name"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="name"
                defaultValue={name}
                onChange={handleNameChange}
              />
            </Grid>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography>Host URL</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                id="outlined-basic2"
                label="Host URL"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="Host URL"
                defaultValue={hostURL}
                onChange={handleHostURLChange}
              />
            </Grid>
            <Grid item xs={2} sx={{ marginY: 'auto' }}>
              <Typography>Credentials</Typography>
            </Grid>
            <Grid item xs={10}>
              <Stack direction="row" spacing={2} alignItems="center">
                <SelectItem
                  defaultValue={accessTokenId}
                  label="Credentials"
                  Items={accessTokenIds}
                  Click={handleItemClickProps}
                />
                <Button
                  variant="outlined"
                  startIcon={<AddIcon />}
                  size="small"
                  onClick={handleOpen}
                  sx={{ color: 'black', borderColor: 'black' }}
                >
                  Add
                </Button>
                <ConnetctModal
                  open={open}
                  Close={handleClose}
                  Change={handleAxiosProps}
                />
              </Stack>
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </Box>
  );
}
