import React, { useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import FormHelperText from '@mui/material/FormHelperText';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { Build } from 'Components/MDClass/BuildData/BuildData';
import Paper from '@mui/material/Paper';
import axios from 'axios';
import ResponseIdNameData, {
  ResponseIdName,
} from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';
import { useStore } from 'Components/Store/DropDownStore/DropDownStore';
import BuildProperty from '../BuildPropertyBox/BuildPropertyBox';

interface VersionTypeAxois {
  name: string;
  buildTool: string[];
  frameworkVersion: string[];
}

interface buildProps {
  index: number;
  buildData: Build;
  DelClick: (value: number) => void;
}

export default function BuildBasicBox({
  index,
  buildData,
  DelClick,
}: buildProps) {
  const framAndLibsDatas = useStore((state) => state.framworkandLib);
  const setFramAndLibsDatas = useStore((state) => state.setFramworkandLib);

  const [name, setName] = useState(buildData.name);
  const [fileDir, setFileDir] = useState(buildData.projectDirectory);
  const [buildPath, setBuildPath] = useState(buildData.buildPath);
  const [frameworkId, setFrameworkId] = useState(buildData.frameworkId);

  const [frameworkName, setFrameworkName] = useState('');
  const [version, setVersion] = useState(buildData.version);
  const [type, setType] = useState(buildData.type);

  const [framAndLibs, setFramAndLibs] = useState<string[]>([]);
  const [versionName, setVersionName] = useState('');
  const [versions, setVersions] = useState<string[]>([buildData.version]);
  const [types, setTypes] = useState<string[]>([buildData.type]);

  const handleNameOnChange = (event: any) => {
    setName(event.target.value);
    buildData.name = event.target.value;
  };

  const handleFileDirOnChange = (event: any) => {
    setFileDir(event.target.value);
    buildData.projectDirectory = event.target.value;
  };
  const handlebuildPathOnChange = (event: any) => {
    setBuildPath(event.target.value);
    buildData.buildPath = event.target.value;
  };

  const handleDelOnClick = () => {
    DelClick(index);
  };

  const handlePropsFLChange = (event: string) => {
    setFrameworkName(event);
    buildData.version = '';
    setVersion('');
    buildData.type = '';
    setType('');
  };

  const handlePropsVersionChange = (event: string) => {
    setVersion(event);
    buildData.version = event;
  };
  const handlePropsTypeChange = (event: string) => {
    setType(event);
    buildData.type = event;
  };
  const handleFrameworkClickProps = (index: number) => {
    setFrameworkId(framAndLibsDatas[index].id);
    buildData.frameworkId = framAndLibsDatas[index].id;

    const params = { typeId: buildData.frameworkId };
    axios
      .get('/api/project/frameworkVersion', { params })
      .then((res) => {
        const data = res.data as VersionTypeAxois;
        setVersion('');
        setType('');

        setVersions([...data.frameworkVersion]);
        setTypes([...data.buildTool]);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    axios
      .get('/api/project/frameworkType')
      .then((res) => {
        const data = res.data as ResponseIdName[];
        setFramAndLibsDatas([...data]);

        const arr = data.map((value) => value.name);
        setFramAndLibs([...arr]);

        framAndLibsDatas.map((value) => {
          if (value.id === buildData.frameworkId) {
            setFrameworkName(value.name);
          }
          return value.name;
        });
      })
      .catch((err) => {
        console.log(err);
      });

    if (frameworkId !== -1) {
      const params = { typeId: frameworkId };
      axios
        .get('/api/project/frameworkVersion', { params })
        .then((res) => {
          const data = res.data as VersionTypeAxois;
          setVersionName(data.name);
          setVersions([...data.frameworkVersion]);
          setTypes([...data.buildTool]);
        })
        .catch((err) => {
          console.log(err);
        });
    }

    return () => {
      setFramAndLibs([]);
      setVersions([]);
      setTypes([]);
    };
  }, []);

  return (
    <Box>
      <Paper sx={{ padding: 3 }}>
        <Grid container spacing={2}>
          <Grid item>
            <Typography>별칭</Typography>
            <TextField
              defaultValue={name}
              label="Name"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Name"
              onChange={handleNameOnChange}
            />
          </Grid>
          <Grid item>
            <Typography>Framework/ Library</Typography>
            <SelectItem
              defaultValue={frameworkName}
              label="Framework/ Library"
              Items={framAndLibs}
              Change={handlePropsFLChange}
              Click={handleFrameworkClickProps}
            />
          </Grid>
          <Grid item>
            <Typography>{versionName} Version</Typography>
            <SelectItem
              defaultValue={version}
              label="Versions"
              Items={versions}
              Change={handlePropsVersionChange}
            />
          </Grid>
          <Grid item>
            <Typography>
              {frameworkName === 'Vue' || frameworkName === 'React'
                ? 'Nginx Use'
                : 'Type'}
            </Typography>
            <SelectItem
              defaultValue={type}
              label="Types"
              Items={types}
              Change={handlePropsTypeChange}
            />
            {frameworkName === 'Vue' || frameworkName === 'React' ? (
              <FormHelperText id="component-helper-text">
                (※ Yes는 하나만)
              </FormHelperText>
            ) : (
              <Box />
            )}
          </Grid>
          <Grid item />
          <Grid item>
            <Typography>File Dir</Typography>
            <TextField
              label="Dir"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Dir"
              defaultValue={fileDir}
              onChange={handleFileDirOnChange}
            />
          </Grid>
          <Grid item>
            <Typography>Build Path</Typography>
            <TextField
              label="Dir"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Dir"
              defaultValue={buildPath}
              onChange={handlebuildPathOnChange}
            />
          </Grid>
        </Grid>
        <Stack direction="row" spacing={2}>
          <Button
            variant="outlined"
            startIcon={<ArrowDropDownIcon />}
            size="small"
            sx={{ color: 'black', borderColor: 'black' }}
          >
            Property
          </Button>
          <Button
            variant="outlined"
            startIcon={<DeleteIcon />}
            size="small"
            onClick={handleDelOnClick}
            sx={{ color: 'black', borderColor: 'black' }}
          >
            Delete
          </Button>
        </Stack>
        <BuildProperty buildValue={buildData} />
      </Paper>
    </Box>
  );
}
